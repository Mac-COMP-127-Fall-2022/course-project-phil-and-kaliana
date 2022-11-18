import io, string

with open('/Users/phil/Dropbox/Personal Files/Documents/COMP 127/7509Mythic/src/Werdill/wordList/words', 'r') as file:
    words = file.read()
    words = words.split()

    words = [word for word in words if len(word) == 5 and not any([word.startswith(i) for i in string.ascii_uppercase])]

with open('/Users/phil/Dropbox/Personal Files/Documents/COMP 127/7509Mythic/src/Werdill/wordList/filteredWords.txt', 'w+') as file:
    file.write('\n'.join(words))