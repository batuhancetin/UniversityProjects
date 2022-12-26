
x = int(input())
y = int(input())
g = int(input())

# DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
liste = []
liste2 = []
j = True
if x == 0 or y == 0:
    print("YOU WON!")
    j = False
for i in range(y):
    liste2.append(0)
for i in range(x + g + 1):
    liste.append([])
if y % 2 == 1:
    y2 = y // 2
else:
    y2 = y // 2 - 1
for i in range(y2):
    liste[0].append(" ")
liste[0].append("@")
for i in range(y // 2):
    liste[0].append(" ")
for i in range(g):
    for a in range(y):
        liste[i + 1].append(" ")
for i in range(x):
    for a in range(y):
        liste[i + 1 + g].append("*")
for i in range(x + g, -1, -1):
    for a in liste[i]:
        print(a, end="")
    print()
print("-" * 72)
time = 0
timep = 0
score = 0
while j == True:
    p = 0
    inp = input("Choose your action!\n")
    inp = inp.lower()
    if inp != "exit":
        time += 1
    if inp == "left":
        if liste[0][0] != "@":
            liste[0].pop(0)
            liste[0].append(" ")
    elif inp == "right":
        if liste[0][-1] != "@":
            liste[0].pop()
            liste[0].insert(0, " ")
    elif inp == "exit":
        j = False
    elif inp == "fire":
        liste2[liste[0].index("@")] += 1
        if liste2[liste[0].index("@")] > x:
            p = timep
            liste2[liste[0].index("@")] = x + 1
        for i in range(g - timep + liste2[liste[0].index("@")] - 1 + p):
            liste[i + 1][liste[0].index("@")] = "|"
            for z in range(x + g, g, -1):
                for a in liste[z]:
                    print(a, end="")
                print()
            for h in range(g, 0, -1):
                for b in liste[h]:
                    print(b, end="")
                print()
            liste[i + 1][liste[0].index("@")] = " "
            for k in liste[0]:
                print(k, end="")
            print()
            print("-" * 72)
        if liste2[liste[0].index("@")] <= x:
            liste[liste2[liste[0].index("@")] + g - timep][liste[0].index("@")] = " "
        if liste2[liste[0].index("@")] > x:
            liste2[liste[0].index("@")] = x
    if time % 5 == 0 and time != 0 and inp != "exit":
        timep += 1
        if g + min(liste2) >= timep:
            for i in range(1, x + g + 1):
                if i != x + g:
                    liste[i] = liste[i + 1]
                else:
                    liste.pop()
                    liste.append([])
                    for z in range(y):
                        liste[-1].append(" ")
    if liste2[liste[0].index("@")] > x:
        liste2[liste[0].index("@")] = x
    if g + min(liste2) < timep:
        print("GAME OVER")
        j = False
    if sum(liste2) == x * y:
        print("YOU WON!")
        j = False
    for i in range(x + g, -1, -1):
        for a in liste[i]:
            print(a, end="")
        print()
    print("-" * 72)
for o in liste2:
    score += o
print("YOUR SCORE: " + str(score))
# DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
