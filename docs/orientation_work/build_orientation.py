from pathlib import Path
from zipfile import ZipFile
from lxml import etree as E
from copy import deepcopy

base = Path(r'C:\Users\Nikola\Documents\projects\breakout\docs')
src = Path(r'C:\Users\Nikola\Downloads\Breakout_Validation.docx')
z = ZipFile(src)
ns = 'http://schemas.openxmlformats.org/wordprocessingml/2006/main'
n = {'w': ns}
def tag(x): return '{' + ns + '}' + x
def el(x, **attrs):
    a = E.Element(tag(x))
    for k, v in attrs.items(): a.set(tag(k), str(v))
    return a
root = E.fromstring(z.read('word/document.xml'))
body = root.find('w:body', n)
old = list(body)
head, para, pagebreak = [deepcopy(old[i]) for i in (9, 10, 8)]
def add(t, heading=False, bold=False):
    p = deepcopy(head if heading else para)
    for c in list(p):
        if c.tag != tag('pPr'): p.remove(c)
    r, rp = el('r'), el('rPr')
    rp.append(el('color', val='000000'))
    if bold: rp.append(el('b'))
    r.append(rp)
    tx = el('t'); tx.text = t; r.append(tx); p.append(r)
    if heading or bold: p.find('w:pPr', n).append(el('keepNext'))
    body.append(p)

for child in list(body)[7:]: body.remove(child)
body[2].find('.//w:t', n).text = 'Orientation'
body[1].find('w:pPr', n).insert(0, el('pStyle', val='Title'))
body.append(deepcopy(old[7])); body.append(pagebreak)
add('1 Introduction', True)
add('In this document, I explore three topics connected to my Breakout project: Game Development, Interactive Media, and Software Design & Engineering. I explain how each topic appears in the game, which jobs it can lead to, and how it relates to my choice for Semester 2. Software Design & Engineering interests me most.')
add('2 Game Development', True)
add('Connection to my Breakout project', bold=True)
add('Breakout is a game built with Java and libGDX. This topic appears in the paddle controls, ball movement, collision detection, brick destruction, scoring, and level progression. These features work together to create the rules and challenge of the game.')
add('Possible jobs', bold=True)
add('Game Developer: builds games and their features. Gameplay Programmer: writes code for movement, collisions, and game rules. Game Designer: develops mechanics, levels, and difficulty.')
add('My interest and Semester 2', bold=True)
add('Game Development could be a direction to explore if I enjoy creating game mechanics and improving how a game plays. I would consider it as an option for Semester 2, but Software Design & Engineering is my main interest.')
add('3 Interactive Media', True)
add('Connection to my Breakout project', bold=True)
add('Interactive Media focuses on how a player uses and understands the game. In Breakout, this includes the start screen, keyboard controls, visual assets, and the display of score, lives, and level. The pause, win, and game-over screens also communicate what is happening and what the player can do next.')
add('Possible jobs', bold=True)
add('Interactive Media Developer: builds interactive digital experiences. UI/UX Designer: designs interfaces and improves usability. Creative Developer: combines programming with visual design and interaction.')
add('My interest and Semester 2', bold=True)
add('This topic could suit me if I enjoy working on the look of a product and making it easier to use. I would consider it if the design and interaction work becomes a stronger interest, while keeping Software Design & Engineering as my first choice.')
body.append(deepcopy(pagebreak))
add('4 Software Design and Engineering', True)
add('Connection to my Breakout project', bold=True)
add('Software Design & Engineering appears in the way the game code is organised and tested. Breakout has separate classes for the ball, paddle, and bricks, as well as classes for game logic, game state, and level building. Separate screen classes manage the different stages of the game. The project also contains automated tests for features such as collisions, scoring, and level progression.')
add('Possible jobs', bold=True)
add('Software Developer: writes and maintains applications. Software Engineer: designs software systems and develops reliable solutions. Software Test Engineer: creates tests, investigates defects, and checks software quality.')
add('My interest and Semester 2', bold=True)
add('Software Design & Engineering interests me the most out of these three topics. In Breakout, it connects to organising the code, understanding how the different parts work together, and checking that the game behaves correctly. I would consider this direction for Semester 2 because I want to explore software development further.')
add('5 Semester 2 Consideration', True)
add('My preferred direction for Semester 2 is Software Design & Engineering. It connects directly to the programming and structure of my Breakout project, and the skills can also be used to build other types of applications. Game Development and Interactive Media remain possible areas to explore, especially where they overlap with programming.')
add('To help confirm my choice, I could extend Breakout with a small feature and focus on planning the code, implementing it, and testing the result. This would give me another opportunity to explore the kind of work involved in Software Design & Engineering.')
body.append(deepcopy(old[-1]))
header = E.fromstring(z.read('word/header1.xml'))
for t in header.xpath('//w:t', namespaces=n):
    if t.text: t.text = t.text.replace('Validation', 'Orientation')
settings = E.fromstring(z.read('word/settings.xml'))
u = settings.find('w:updateFields', n)
if u is None: u = el('updateFields'); settings.append(u)
u.set(tag('val'), 'true')
def xml(t): return E.tostring(t, encoding='UTF-8', xml_declaration=True, standalone=True)
patches = {'word/document.xml': xml(root), 'word/header1.xml': xml(header), 'word/settings.xml': xml(settings)}
out = base / 'Breakout_Orientation.docx'
with ZipFile(out, 'w') as dest:
    for entry in z.infolist(): dest.writestr(entry, patches.get(entry.filename, z.read(entry.filename)))
print(out)
