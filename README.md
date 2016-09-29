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
