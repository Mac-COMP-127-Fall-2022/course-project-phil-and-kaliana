import io, string, json, time, os


def filterAllBut4Ltrs():
    with open('res/wordList/words', 'r') as file:
        words = file.read()
        words = words.split()

        words = {word: None for word in words if len(word) == 4 and not any([word.startswith(i) for i in string.ascii_uppercase])}

    with open(f'res/wordList/4words{int(time.time())}.json', 'w+') as file:
        file.write(json.dumps(words, indent = 2))

filterAllBut4Ltrs()

exit()

with open('res/wordList/4words.json', 'r') as file:
    words = json.loads(file.read())

def makeWordsBackup():
    with open(f'res/wordList/backupWordList/4words_{int(time.time())}.json', 'w+') as file:
        file.write(json.dumps(words, indent = 2))

print(words)

def isPlural(words):
    makeWordsBackup()
    try:
        print("===== SELECT WHETHER WORDS CAN BE PLURALIZED =====\n\nHit enter/return to designate word as NOT a valid plural\n\n^C to exit\n\n'undo' to reverse last word's designation\n\nanything else to indicate valid plural")
        lastWord = ""
        for w in words:
            if not words[w] == None:
                continue
            while True:
                input_ = input(f"\n=====\n{w}s\n")
                if input_ == 'undo' and lastWord: 
                    words[lastWord] = not words[lastWord]
                    continue
                if not input_:
                    words[w] = False
                else:
                    words[w] = True
                break
            lastWord = w

            with open(f'res/wordList/4words.json', 'w+') as file:
                file.write(json.dumps(words, indent = 2))

    except KeyboardInterrupt:
        with open(f'res/wordList/4words.json', 'w+') as file:
            file.write(json.dumps(words, indent = 2))

isPlural(words)
