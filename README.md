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

|  |  |  |  |  |  |  |
| :--------------------: | :-: | :-: | :-: | :-: | :-: | :-: |
| **Phonemes of Word 1** | AH | P | R | AY | T |  |
| **Phonemes of Word 2** | AH | P | T | AY | T |  |
| **Points Awarded** | 5 | 1 | 0.5 | 5 | 1 | **Total RV: 12.5** |
