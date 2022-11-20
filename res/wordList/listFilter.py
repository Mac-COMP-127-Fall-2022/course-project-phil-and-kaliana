import io, string, json, time, os

def filterAllBut5Ltrs():
    with open('res/wordList/words', 'r') as file:
        words = file.read()
        words = words.split()

        words = [{word: {}} for word in words if len(word) == 5 and not any([word.startswith(i) for i in string.ascii_uppercase])]

    with open(f'res/wordList/words{int(time.time())}.json', 'w+') as file:
        file.write(json.dumps(words, indent = 2))


# filterAllBut5Ltrs()

with open('res/wordList/words.json', 'r') as file:
    words = json.loads(file.read())

def makeWordsBackup():
    with open(f'res/wordList/backupWordList/words_{int(time.time())}.json', 'w+') as file:
        file.write(json.dumps(words, indent = 2))

print(words)

with open('res/wordList/letterFrequencies.json', 'r') as file:
    frequencies = json.loads(file.read())

# print(frequencies)

def findAndSetFrequencyValues(words, frequencies):
    for word in words:
        frequency = 0
        for i in range(5):
            frequency += frequencies[i][word[i]]
        words[word]["frequencyScore"] = frequency

    with open(f'res/wordList/words{int(time.time())}.json', 'w+') as file:
        file.write(json.dumps(words, indent = 2))

def setSolution(words):
    makeWordsBackup()
    try:
        print("===== SELECT WHETHER WORDS SHOULD BE IN SOLUTION LIST =====\n\nHit enter/return to designate word as NOT a solution word\n\n^C to exit\n\n'undo' to reverse last word's designation\n\nanything else to designate word as a valid solution")
        lastWord = ""
        for w in words:
            if "isSolution" in words[w]:
                continue
            while True:
                input_ = input(f"\n=====\n{w}\n")
                if input_ == 'undo' and lastWord: 
                    words[lastWord]["isSolution"] = not words[lastWord]["isSolution"]
                    continue
                if not input_:
                    words[w]["isSolution"] = False
                else:
                    words[w]["isSolution"] = True
                break
            lastWord = w

            with open(f'res/wordList/words.json', 'w+') as file:
                file.write(json.dumps(words, indent = 2))

    except KeyboardInterrupt:
        with open(f'res/wordList/words.json', 'w+') as file:
            file.write(json.dumps(words, indent = 2))

setSolution(words)
# findAndSetFrequencyValues(words, frequencies)

