LZEncryption
============

CS 311 Fall 2014 Homework 4
Project: Lempel-Ziv


1 Introduction


In this homework, you will implement the Lempel-Ziv (LZ) compression
algorithm using various methods. You will then analyze these methods and
conrm the analysis experimentally. LZ and its variants are widely used in
many le compression utilities such as UNIX compress, gzip, and the GIF
picture le format. There are many variants; you will implement the variant
known as LZ78, developed by Abraham Lempel and Jacob Ziv in 1978 [1].
You must use Java to implement your solution and turn in only your source
les. Unlike written assignments, this programming project must be turned
in electronically via the standard Blackboard upload. Further details on
le names and how to package your source les are described later in this
document.


2 Tries
We write  to denote the empty string, the string of length 0. We say a
string x is a prex of a string y if the length of x is at most the length
of y, and they agree on every character position where they overlap. For
example, aba is a prex of abaaab, abab, aba, ababba, but not of baba
or ab.  is a prex of every string. Given two strings x and y, xy denotes y
concatenated to the end of x.
A trie (pronounced `try') is a binary tree used to store a set of strings.
Each node in the trie represents one of the strings in the set, by examining
the path from the root to that node. In the example we see a trie for binary
strings (0 and 1 are the only characters) interpreting left to mean 0 and
right to mean 1. A trie with a larger character set such as 0 through 9
would mean that each node in the tree could have as many as 10 children.
1
λ
0 1
00
001
01
010 011
Figure 1: A trie representing the set f; 0; 1; 00; 01; 001; 010; 011g. In the
gure, each node is labeled with the string it represents, but when imple-
menting a trie, it is unnecessary to actually store this string in the node,
since the path from the root uniquely identies the string.

2
If a trie must represent strings from the 256 characters in the ASCII code,
then a node could have as many as 256 children.
Note that a trie can only represent a set of strings that has the following
property: if the string x is in the set, then every prex of x is also in the
set, because every ancestor of a node represents a prex of the node's string.
The trie is a very compact representation of such a set.


3 The Lempel-Ziv Compression Algorithm


The LZ compression algorithm works as follows on input string x. It parses
x into consecutive substrings, termed phrases, no two of which are equal
to each other (except that possibly the last phrase is equal to a previous
phrase). Each phrase is assigned a codeword, another string which represents
the phrase in the compressed output string, and the codewords are simply
concatenated together to create the output. If the input string has a lot of
redundancy (meaning it has substrings that are repeated more often than
would be expected in a randomly chosen string), then most of the codewords
will be shorter than the phrases they represent, and compression will happen.
The phrases are determined incrementally as follows: the rst phrase of
x is ,1 and given the phrases so far, the next phrase of x is the shortest
substring of x that is not already a phrase, starting after the end of the
previous phrase. The nal phrase is the only phrase that may be repeated,
if there are not enough bits remaining in x to create a new phrase. Note
that because the next phrase is the shortest substring of x that has not been
a phrase before, if p is a phrase in x, then all prexes of p are also phrases
in x, occurring before p.
Example 3.1. x = 00101010101010101 is parsed as 0 01 010 1 0101 01010
1.
Example 3.2. x = abcabadcbacdabcabcabc is parsed as a b c ab ad cb ac d
abc abca bc.
Example 3.3. x = 000000000000000000 is parsed as 0 00 000 0000 00000
000.
The codewords for each phrase are computed in the following way. The
set of all phrases seen so far are stored in a data structure called the dictio-
nary, which starts initially with only the string . Informally, to determine
1We leave out writing  in the parsing, with it understood that  is always the rst
phrase, though invisible.
3
the next codeword for the (n + 1)th phrase p, nd the dictionary index (or-
der of addition to the dictionary) of the one-character-shorter phrase that p
extends, represent that integer in binary using dlog2 ne bits (log2 n rounded
up), and add the nal bit of p to the end of that. This new codeword is
then added to the dictionary.
More formally:
 Every string p (except ) has a prex that is one character shorter than
itself; call this pref(p). For instance, If strings are binary, pref(0) =
pref(1) = ; pref(00) = pref(01) = 0; pref(10) = pref(11) = 1; pref(000) =
pref(001) = 00; pref(010) = pref(011) = 01, etc. Because each phrase
extends a previous phrase by 1 character, if p is a phrase in x, then
pref(p) will also be a phrase in x.
 Every phrase p appears in some order in x; call this integer its index
index(p), with index() = 0. For instance, in Example 3.2, index(a) =
1; index(b) = 2; index(abc) = 9, etc.
 If a phrase p extends pref(p) by the character b, then p's codeword is
bin2(index(pref(p)))b, where bin2(index(pref(p))) is the binary repre-
sentation of the integer index(pref(p)) to dlog(n)e bits, where n is the
total number of parsings in the string to encode.
Output this codeword, add the phrase p to the dictionary, and search
for the next phrase. If the nal phrase has occurred before (the most
likely scenario for most input strings), then do not output its nal bit
as part of the codeword; simply output its index.
Example 3.4. The parsing of input string x = aabaaabaaaaaabababbbbaba
and its compressed representation z are shown below.2 The phrases in the
parsing of x are separated, as are the associated codewords in z, and the
nal copied character of the phrase and codeword is italicized, so that the
unitalicized part of each block of z represents the index of the previous
phrase that was extended. Also shown at each step are the index (written
in base 10) of the current phrase p and of pref(p) (the one-character-shorter
prex of the current phrase). Note that the codeword consists of an index
plus a copied character, so for 8-bit characters, the index is 8 less than the
total length of the codeword. In the example below, there are 10 phrases, and
2Note that in this example, z is actually longer than x, which is to be expected for
most inputs. Therefore, do not be surprised if most of the inputs you create for the LZ
algorithm do not compress, unless you take care to make the input redundant.
4
so at most 10 codewords are added to the dictionary. Thus, each codeword
is represented by 4 bits.
x = a ab aa aba aaa aab abab b bb aba
z = 0a 1b 1a 2a 3a 3b 4b 0b 8b 4
The compressed output that is stored to disk is a 32-bit binary integer
that species the number of bits in each codeword followed by the binary
data described above. Note that all data is packed with no wasted bits.
Note also that all strings read are represented by 8 bits.
To decode a compressed string z, the algorithm builds the same dictio-
nary that was built during the compression. The dictionary starts as fg.
The size of the codewords (in bits) is read rst. Then, the rst codeword is
read from the input. Becasue the dictionary can be rebuilt from the code-
words, subsequent codewords that are read are easily decoded. The entire
encoding algorithm is designed to ensure that the information needed to de-
code the compressed data is always computable by the decoding algorithm,
so that the dictionary does not need to be transmitted with the compressed
data. Use the index part of the codeword to look up the string in the dictio-
nary to output, and then output the nal copied bit of the codeword after
the decoded string is output. Add the whole phrase (decoded word with the
copied character on the end) to the dictionary. Repeat until done.
Recall that whenever a string is added to the dictionary, its one-character-
shorter prex is already in the dictionary. This implies a useful property of
the dictionary: if a string is in the dictionary, then every one of its prexes
are in the dictionary as well. Therefore a trie data structure is especially
ecient at representing the dictionary, and in searching for matching sub-
strings while processing the input.
4 Your Assignment
Write a non-instantiable class (i.e. with one private default constructor)
called LZ, with two static methods,
public static String encode(String uncompressed),
and
public static String decode(String compressed).
These methods use the LZ algorithm to compress and decompress their
inputs, respectively. Assume that each character in the String parameters
represents 8-bit characters, and assume that decode is only called with
5
correctly compressed input. However, LZ.encode must handle any string,
including  (i.e., "").
You are free to add any other private or public methods and classes that
you would like. You are free to use any interfaces, classes, etc., in the Java
Standard Library. However, the LZ dictionary must be stored in a trie
data structure of your own creation. Turn in the le LZ.java and any other
source les you create. Place all such les in the default package.
Feel free to consult external sources in learning about LZ compression,
but cite any such sources in your homework les, and use this document as
the nal reference for how LZ is supposed to work, since most descriptions
of LZ are dierent in at least one way from the description given here.
Important note: The next programming assignment (part b) will utilize
hash tables instead of tries to encode dictionaries, and you will be performing
analysis and testing to compare the two data structures. Modular coding
and good design will be very helpful and save you time in the long run.
References
[1] J. Ziv and A. Lempel. Compression of individual sequences via variable-
rate coding. IEEE Transactions on Information Theory, 24:530{536,
1978.
6
