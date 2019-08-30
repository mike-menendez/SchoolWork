w, d, r, s = [input() for _ in range(int(input().strip()))], {}, "", " "
for i in range(len(w)):
    if w[i][0] not in d.keys(): d[w[i][0]] = list(set(w[i].replace(w[i][0], "")))
    else:
        for j in range(len(w[i])): d[w[i][0]] = str(d[w[i][0]]) + " ".join(filter(lambda x: x not in d[w[i][0]], w[i].replace(w[i][0], "")))
for k in list(d.keys()): r = r + " " + str(k)
for k in d.keys(): s = s + " ".join(filter(lambda x: x not in',\'"[]', list(filter(lambda x: x not in r and x not in s, d[k]))))
print((r+s)[1:])
