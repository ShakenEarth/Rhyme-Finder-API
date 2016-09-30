# Pronunciation Similarity Algorithm (Version 1)

The algorithm uses the [CMU Pronouncing Dictionary](http://www.speech.cs.cmu.edu/cgi-bin/cmudict) which contains the spelling of each word in English as well as its [ARPAbet](https://en.wikipedia.org/wiki/Arpabet) translation.

There are two cases encountered when comparing two words for pronunciation similarity: either they have the same phonemic length (that is, they are composed of the same number of phonemes) or they have differing phonemic lengths. In either of these cases, the algorithm awards the same number of pronunciation “points” between phonemes:

| **Case**      | **Number of Points Awarded** |
| :-------------: |:-------------:         |
| Identical Vowel Phonemes        | 5         |
| Different Vowel Phonemes        | 1               |
| Identical Consonant Phonemes   | 1              |
| Different Consonant Phonemes   | 0.5               |

### Some Basic Definitions

* **Rhyme Value**: the sum of the points awarded between phonemes for any two given words as well deductions when necessary.
* **Homophonic Rhyme Value**: the Rhyme Value given when comparing a word to itself.
* **Rhyme Percentile**: ((Rhyme Value)/(Homophonic Rhyme Value)) * 100%

## Pronunciation Similarity Between Words of Same Phonemic Length

Finding pronunciation similarity between words that have the same number of phonemes is relatively straightforward and can be broken down into three steps:

1. Iterate through each phoneme in one of the words and compare it to its corresponding phoneme in the other word, adding the resulting points awarded to the total Rhyme Value along the way.
2. Find Homophonic Rhyme Value (as previously defined).
3. Divide Rhyme Value by Homophonic Rhyme Value and multiply by 100.

### Example:

Let’s choose two words that have the same phonemic length and compare them to one another: *upright* [AH P R AY T] and *uptight* [AH P T AY T].

#### Step 1

|  |  |  |  |  |  |  |
| :--------------------: | :-: | :-: | :-: | :-: | :-: | :-: |
| **Phonemes of Word 1** | AH | P | R | AY | T |  |
| **Phonemes of Word 2** | AH | P | T | AY | T |  |
| **Points Awarded** | 5 | 1 | 0.5 | 5 | 1 | **Total RV: 12.5** |
 
#### Step 2
|  |  |  |  |  |  |  |
| :--------------------: | :-: | :-: | :-: | :-: | :-: | :-: |
| **Phonemes of Word 1** | AH | P | R | AY | T |  |
| **Phonemes of Word 1** | AH | P | R | AY | T |  |
| **Points Awarded** | 5 | 1 | 1 | 5 | 1 | **Total HRV: 13** |
(note that performing this same operation on *uptight* would result in the same total)
 
#### Step 3
(12.5/13)*100% = 96%

## Pronunciation Similarity Between Words of Differing Phonemic Length

The process of finding pronunciation similarity between two words of differing phonemic lengths is a bit trickier since simple iteration through the phonemes of a word is simply not enough to gain an accurate picture of whether or not two words have similarity in pronunciation. While the last two steps may remain the same, it is necessary to test a variety of combinations and then give the benefit of the doubt to the combination with the highest Rhyme Value output. This process can be rather hard to explain, so I’ll try and explain by example first:

Let’s say we’re comparing the words *shifter* (SH IH F T ER) and *ship* (SH IH P). (These were chosen because they only differ in size by two phonemes which makes them an easy example to showcase). First, we must identify which word is phonemically longer and which is shorter. In this case *ship* is the shorter word and *shifter* is the longer word.

Once we have identified which word is shorter and which is longer, we then compare the first phoneme of the shorter word to every phoneme in the longer word and then store the positions in the longer words where there were points awarded:
