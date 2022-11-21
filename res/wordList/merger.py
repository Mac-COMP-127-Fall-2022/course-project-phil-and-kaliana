import io, string, json, time, os

with open('res/wordList/words.json', 'r') as file:
    words = json.loads(file.read())

with open('res/wordList/valid-wordle-words.txt', 'r') as file:
    wordles = file.read()
    wordles = wordles.split()

for w in wordles:
    if w not in words:
        words[w] = {}

with open(f'res/wordList/words{int(time.time())}.json', 'w+') as file:
    words = {w: words[w] for w in sorted(words)}
    file.write(json.dumps(words, indent = 2))