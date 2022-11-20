import io, string, json, time, os

with open('res/wordList/words.json', 'r') as file:
    words = json.loads(file.read())

solutions = []
for w in words:
    try:
        if words[w]["isSolution"]:
            solutions.append(w)
    except KeyError: continue

with open(f'res/wordList/words.txt', 'w+') as file:
    file.write("\n".join(words.keys()))

with open(f'res/wordList/solutions.txt', 'w+') as file:
    file.write("\n".join(solutions))
