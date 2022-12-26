import re
import sys
opening = open("calc.in", "r")
writing = open("calc.out", "w")
s = ""
s1 = ""
s2 = ""
s3 = ""
digits = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
t_digits = ["sifir", "bir", "iki", "uc", "dort", "bes", "alti", "yedi", "sekiz", "dokuz"]
l_term = ["dogru", "yanlis"]
float_term = []
binaop = ["+", "-", "*", "arti", "eksi", "carpi"]
binlop = ["ve", "veya"]
for i in digits:
    for a in digits:
        float_term.append(i + "." + a)
varnamelist = []
a_varname = []
l_varname = []
keywords = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'sifir', 'bir', 'iki', 'uc', 'dort', 'bes', 'alti', 'yedi', 'sekiz', 'dokuz', 'dogru', 'yanlis', '+', '-', '*', 'arti', 'eksi', 'carpi', 've', 'veya', '(', ')', 'ac-parantez', 'kapa-parantez', 'AnaDegiskenler', 'YeniDegiskenler', 'Sonuc', 'degeri', 'olsun', 'nokta']
for line in opening:
    s = s + line
s1 = re.findall("^([\w\W]*)YeniDegiskenler", s)
if s1 == []:
    writing.write("Dont Let Me Down")
    sys.exit()
s1 = s1[0].split("\n")
while "" in s1:
    s1.remove("")
for i in range(len(s1)):
    s1[i] = s1[i] + "\n"
if re.search("\s*AnaDegiskenler\s*", s1[0]):
    pass
else:
    writing.write("Dont Let Me Down")
    sys.exit()
if re.findall("\s*AnaDegiskenler\s*", s1[0])[0] != s1[0]:
    writing.write("Dont Let Me Down")
    sys.exit()
s1.pop(0)
for i in s1:
    if re.search(".*\sdegeri\s.*\solsun\n", i):
        pass
    else:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.findall("\s*(.*?)\s*degeri", i)[0].isalnum() == False:
        writing.write("Dont Let Me Down")
        sys.exit()
    if len(re.findall("\s*(.*?)\s*degeri", i)[0]) > 10:
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s*(.*?)\s*degeri", i)[0] in keywords:
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s*(.*?)\s*degeri", i)[0] in varnamelist:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.findall("[\s\S]{1}\.[\s\S]{1}",i) != re.findall("[0-9]{1}\.[0-9]{1}",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s[\S]+\.[\S]+\s",i) != re.findall("\s[0-9]{1}\.[0-9]{1}\s",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    for g in re.findall("degeri\s+(.*?)\s+olsun\n", i)[0].split():
        if g not in digits + t_digits + float_term + l_term + ["nokta"]:
            writing.write("Dont Let Me Down")
            sys.exit()
    if re.findall("degeri\s+(.*?)\s+olsun\n", i)[0] in digits + t_digits + float_term + l_term:
        pass
    elif re.findall("nokta",i) == []:
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("degeri\s+(.*?)\s+nokta", i)[0] in t_digits and re.findall("nokta\s+(.*?)\s+olsun\n", i)[0] in t_digits:
        pass
    else:
        writing.write("Dont Let Me Down")
        sys.exit()
    varnamelist.append(re.findall("\s*(.*?)\s*degeri", i)[0])
    if re.findall("degeri\s+(.*?)\s+olsun\n", i)[0] in digits or re.findall("degeri\s+(.*?)\s+olsun\n", i)[0] in t_digits or re.findall("degeri\s+(.*?)\s+olsun\n", i)[0] in float_term:
        a_varname.append(re.findall("\s*(.*?)\s*degeri", i)[0])
    elif re.findall("degeri\s+(.*?)\s+olsun\n", i)[0] in l_term:
        l_varname.append(re.findall("\s*(.*?)\s*degeri", i)[0])
    elif re.findall("degeri\s+(.*?)\s+nokta", i)[0] in t_digits and re.findall("nokta\s+(.*?)\s+olsun\n", i)[0] in t_digits:
        a_varname.append(re.findall("\s*(.*?)\s*degeri", i)[0])
    else:
        writing.write("Dont Let Me Down")
        sys.exit()
s2 = re.findall("(YeniDegiskenler[\w\W]*)Sonuc", s)
if s2 == []:
    writing.write("Dont Let Me Down")
    sys.exit()
s2 = s2[0].split("\n")
while "" in s2:
    s2.remove("")
for i in range(len(s2)):
    s2[i] = s2[i] + "\n"
if re.search("^\s*YeniDegiskenler\s*", s2[0]):
    pass
else:
    writing.write("Dont Let Me Down")
    sys.exit()
if re.findall("^\s*YeniDegiskenler\s*", s2[0])[0] != s2[0]:
    writing.write("Dont Let Me Down")
    sys.exit()
s2.pop(0)
for i in s2:
    if re.search("^.*\sdegeri\s.*\solsun\n", i):
        pass
    else:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.findall("^\s*(.*?)\s*degeri", i)[0].isalnum() == False:
        writing.write("Dont Let Me Down")
        sys.exit()
    if len(i.split()[0]) > 10:
        writing.write("Dont Let Me Down")
        sys.exit()
    elif i.split()[0] in keywords:
        writing.write("Dont Let Me Down")
        sys.exit()
    elif i.split()[0] in varnamelist:
        writing.write("Dont Let Me Down")
        sys.exit()
    varnamelist.append(i.split()[0])
    if len(re.findall("\(",i)) + len(re.findall("ac-parantez",i)) != len(re.findall("\)",i)) + len(re.findall("kapa-parantez",i)):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\(\s*\)", i) or re.search("ac-parantez\s*\)", i) or re.search("\(\s*kapa-parantez", i) or re.search("ac-parantez\s*kapa-parantez", i):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S\(|\(\S", re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Sac-parantez|ac-parantez\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Skapa-parantez|kapa-parantez\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\S\)|\)\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\S\+|\+\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("[()]-|-[()]",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\S\*|\*\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Sarti|arti\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Seksi|eksi\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Scarpi|carpi\S",re.findall("degeri(.*)olsun\n",i)[0]) or re.search("\Sveya|veya\S",re.findall("degeri(.*)olsun\n",i)[0]):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.findall("[\s\S]{1}\.[\s\S]{1}",i) != re.findall("[0-9]{1}\.[0-9]{1}",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s[\S]+\.[\S]+\s",i) != re.findall("\s[0-9]{1}\.[0-9]{1}\s",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("veya|dogru|yanlis|\sve\s", re.findall("degeri(.*)olsun\n",i)[0]) and re.search("\+|\s-\s|\*|arti|eksi|carpi|\s[0-9]\s|\s[0-9]{1}\.[0-9]{1}\s", re.findall("degeri(.*)olsun\n",i)[0]):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.search("veya|dogru|yanlis|\sve\s", re.findall("degeri(.*)olsun\n",i)[0]):
        for d in a_varname:
            if d in re.findall("degeri\s(.*)\solsun\n", i)[0].split() != -1:
                writing.write("Dont Let Me Down")
                sys.exit()
    elif re.search("\\+|\s-\s|\*|arti|eksi|carpi|\s[0-9]\s|\s[0-9]{1}\.[0-9]{1}\s", re.findall("degeri(.*)olsun\n",i)[0]):
        for d in l_varname:
            if d in re.findall("degeri\s(.*)\solsun\n", i)[0].split() != -1:
                writing.write("Dont Let Me Down")
                sys.exit()
    countacparantez = 0
    countkapaparantez = 0
    for p in i.split():
        if p == "ac-parantez" or p == "(":
            countacparantez += 1
        elif p == "kapa-parantez" or p == ")":
            countkapaparantez += 1
        if countkapaparantez > countacparantez:
            writing.write("Dont Let Me Down")
            sys.exit()
    if re.search("\+\s+\)|\*\s+\)|arti\s+\)|eksi\++\)|carpi\s+\)|veya\s+\)", i) or re.search("\+\s+kapa-parantez|\*\s+kapa-parantez|arti\s+kapa-parantez|eksi\s+kapa-parantez|carpi\s+kapa-parantez|veya\s+kapa-parantez", i) or re.search("\(\s+\+|\(\s+\*|\(\s+arti|\(\s+eksi|\(\s+carpi|\(\s+veya",i) or re.search("ac-parantez\s+\+|ac-parantez\s+\*|ac-parantez\s+arti|ac-parantez\s+eksi|ac-parantez\s+carpi|ac-parantez\s+veya",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    for l in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
        if l in digits + t_digits + float_term + l_term + binaop + binlop + a_varname + l_varname + varnamelist + ["ac-parantez","kapa-parantez","(",")","nokta"]:
            pass
        else:
            writing.write("Dont Let Me Down")
            sys.exit()
    county = -1
    for t in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
        county += 1
        if t == "nokta":
            if county == 0 or county == len(re.findall("degeri\s(.*)\solsun\n", i)[0].split()) - 1:
                writing.write("Dont Let Me Down")
                sys.exit()
            elif re.findall("degeri\s(.*)\solsun\n", i)[0].split()[county-1] not in t_digits or re.findall("degeri\s(.*)\solsun\n", i)[0].split()[county+1] not in t_digits:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.findall("degeri\s(.*)\solsun\n", i)[0].split()[0] in binlop + binaop or re.findall("degeri\s(.*)\solsun\n", i)[0].split()[-1] in binlop + binaop:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+arti|\S+\s+eksi|\S+\s+carpi|\S+\s+\+|\S+\s+\*|\S+\s+veya|\S+\s+ve\s|\S+\s+-", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("\S+\s+arti|\S+\s+eksi|\S+\s+carpi|\S+\s+\+|\S+\s+\*|\S+\s+veya|\S+\s+ve\s|\S+\s+-", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("arti\s+\S+|eksi\s+\S+|carpi\s+\S+|\+\s+\S+|\*\s+\S+|veya\s+\S+|-\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("arti\s+\S+|eksi\s+\S+|carpi\s+\S+|\+\s+\S+|\*\s+\S+|veya\s+\S+|-\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.findall("degeri\s(.*)\solsun\n", i)[0].split()[-1] in ["ac-parantez", "("]:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+ac-parantez|\S+\s+\(", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("\S+\s+ac-parantez|\S+\s+\(", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + binaop + binlop + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("ac-parantez\s+\S+|\(\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("ac-parantez\s+\S+|\(\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.findall("degeri\s(.*)\solsun\n", i)[0].split()[0] in ["kapa-parantez", ")"]:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+kapa-parantez|\S+\s+\)", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("\S+\s+kapa-parantez|\S+\s+\)", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("kapa-parantez\s+\S+|\)\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        for j in re.findall("kapa-parantez\s+\S+|\)\s+\S+", re.findall("degeri\s(.*)\solsun\n", i)[0]):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + binaop + binlop + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
    countx = -1
    for j in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
        countx += 1
        if j in digits + t_digits + float_term + l_term + varnamelist:
            for t in re.findall("degeri\s(.*)\solsun\n", i)[0].split()[countx + 1:]:
                if t in binlop + binaop + ["nokta"]:
                    break
                elif t in digits + t_digits + float_term + l_term + varnamelist:
                    writing.write("Dont Let Me Down")
                    sys.exit()
    varnamelist.append(i.split()[0])
    if re.search("\+|\s-\s|\*|arti|eksi|carpi", re.findall("degeri\s+(.*?)\s+olsun\n", i)[0]):
        a_varname.append(i.split()[0])
    for l in a_varname:
        if l in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
            a_varname.append(i.split()[0])
            break
    for t in digits + t_digits + float_term:
        if t in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
            a_varname.append(i.split()[0])
            break
    if re.search("veya|dogru|yanlis|\sve\s", re.findall("degeri\s(.*)\solsun\n", i)[0]):
        l_varname.append(i.split()[0])
    for r in l_varname:
        if r in re.findall("degeri\s(.*)\solsun\n", i)[0].split():
            l_varname.append(i.split()[0])
s3 = re.findall("(Sonuc[\w\W]*$)", s)
if s3 == []:
    writing.write("Dont Let Me Down")
    sys.exit()
s3 = s3[0].split("\n")
while "" in s3:
    s3.remove("")
for i in range(len(s3)):
    s3[i] = s3[i] + "\n"
if re.search("\s*Sonuc\s*", s3[0]):
    pass
else:
    writing.write("Dont Let Me Down")
    sys.exit()
if re.findall("\s*Sonuc\s*", s3[0])[0] != s3[0]:
    writing.write("Dont Let Me Down")
    sys.exit()
s3.pop(0)
if len(s3) > 1:
    writing.write("Dont Let Me Down")
    sys.exit()
for i in s3:
    if len(re.findall("\(",i)) + len(re.findall("ac-parantez",i)) != len(re.findall("\)",i)) + len(re.findall("kapa-parantez",i)):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\(\s*\)", i) or re.search("ac-parantez\s*\)", i) or re.search("\(\s*kapa-parantez", i) or re.search("ac-parantez\s*kapa-parantez", i):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S\(|\(\S", i) or re.search("\Sac-parantez|ac-parantez\S",i) or re.search("\Skapa-parantez|kapa-parantez\S",i) or re.search("\S\)|\)\S",i) or re.search("\S\+|\+\S",i) or re.search("[()]-|-[()]",i) or re.search("\S\*|\*\S",i) or re.search("\Sarti|arti\S",i) or re.search("\Seksi|eksi\S",i) or re.search("\Scarpi|carpi\S",i) or re.search("\Sveya|veya\S",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("veya|dogru|yanlis|\sve\s", i) and re.search("\+|\s-\s|\*|arti|eksi|carpi|\s[0-9]\s|\s[0-9]{1}\.[0-9]{1}\s", i):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.search("veya|dogru|yanlis|\sve\s", i):
        for d in a_varname:
            if d in i.split() != -1:
                writing.write("Dont Let Me Down")
                sys.exit()
    elif re.search("\+|\s-\s|\*|arti|eksi|carpi|\s[0-9]\s|\s[0-9]{1}\.[0-9]{1}\s", i):
        for d in l_varname:
            if d in i.split() != -1:
                writing.write("Dont Let Me Down")
                sys.exit()
    countacparantez = 0
    countkapaparantez = 0
    for p in i.split():
        if p == "ac-parantez" or p == "(":
            countacparantez += 1
        elif p == "kapa-parantez" or p == ")":
            countkapaparantez += 1
        if countkapaparantez > countacparantez:
            writing.write("Dont Let Me Down")
            sys.exit()
    if re.findall("[\s\S]{1}\.[\s\S]{1}",i) != re.findall("[0-9]{1}\.[0-9]{1}",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s[\S]+\.[\S]+\s",i) != re.findall("\s[0-9]{1}\.[0-9]{1}\s",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    #ve
    if re.search("\+\s+\)|\*\s+\)|arti\s+\)|eksi\++\)|carpi\s+\)|veya\s+\)", i) or re.search("\+\s+kapa-parantez|\*\s+kapa-parantez|arti\s+kapa-parantez|eksi\s+kapa-parantez|carpi\s+kapa-parantez|veya\s+kapa-parantez", i) or re.search("\(\s+\+|\(\s+\*|\(\s+arti|\(\s+eksi|\(\s+carpi|\(\s+veya",i) or re.search("ac-parantez\s+\+|ac-parantez\s+\*|ac-parantez\s+arti|ac-parantez\s+eksi|ac-parantez\s+carpi|ac-parantez\s+veya",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    for u in i.split():
        if u in digits + t_digits + float_term + l_term + binaop + binlop + a_varname + l_varname + varnamelist + ["ac-parantez","kapa-parantez","(",")","nokta"]:
            pass
        else:
            writing.write("Dont Let Me Down")
            sys.exit()
    if re.findall("[\s\S]{1}\.[\s\S]{1}",i) != re.findall("[0-9]{1}\.[0-9]{1}",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    elif re.findall("\s[\S]+\.[\S]+\s",i) != re.findall("\s[0-9]{1}\.[0-9]{1}\s",i):
        writing.write("Dont Let Me Down")
        sys.exit()
    county = -1
    for t in i.split():
        county += 1
        if t == "nokta":
            if county == 0 or county == len(i.split()) - 1:
                writing.write("Dont Let Me Down")
                sys.exit()
            elif i.split()[county - 1] not in t_digits or i.split()[county + 1] not in t_digits:
                writing.write("Dont Let Me Down")
                sys.exit()
    if i.split()[0] in binlop + binaop or i.split()[-1] in binlop + binaop:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+arti|\S+\s+eksi|\S+\s+carpi|\S+\s+\+|\S+\s+\*|\S+\s+veya|\S+\s+ve\s|\S+\s+-", i):
        for j in re.findall("\S+\s+arti|\S+\s+eksi|\S+\s+carpi|\S+\s+\+|\S+\s+\*|\S+\s+veya|\S+\s+ve\s|\S+\s+-", i):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("arti\s+\S+|eksi\s+\S+|carpi\s+\S+|\+\s+\S+|\*\s+\S+|veya\s+\S+|ve\s+\S+|-\s+\S+", i):
        for j in re.findall("arti\s+\S+|eksi\s+\S+|carpi\s+\S+|\+\s+\S+|\*\s+\S+|veya\s+\S+|ve\s+\S+|-\s+\S+", i):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if i.split()[-1] in ["ac-parantez", "("]:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+ac-parantez|\S+\s+\(", i):
        for j in re.findall("\S+\s+ac-parantez|\S+\s+\(", i):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + binaop + binlop + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("ac-parantez\s+\S+|\(\s+\S+",i):
        for j in re.findall("ac-parantez\s+\S+|\(\s+\S+",i):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["ac-parantez", "("]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if i.split()[0] in ["kapa-parantez", ")"]:
        writing.write("Dont Let Me Down")
        sys.exit()
    if re.search("\S+\s+kapa-parantez|\S+\s+\)", i):
        for j in re.findall("\S+\s+kapa-parantez|\S+\s+\)", i):
            if j.split()[0] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
    if re.search("kapa-parantez\s+\S+|\)\s+\S+",i):
        for j in re.findall("kapa-parantez\s+\S+|\)\s+\S+",i):
            if j.split()[1] not in digits + t_digits + float_term + l_term + a_varname + l_varname + varnamelist + binaop + binlop + ["kapa-parantez", ")"]:
                writing.write("Dont Let Me Down")
                sys.exit()
writing.write("Here Comes the Sun")
opening.close()
writing.close()