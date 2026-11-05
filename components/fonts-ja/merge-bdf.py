"""Merge Shinonome 12-dot BDFs into one Unicode BDF whose glyphs are named U+XXXX
(the naming MIDPath's BDFFontContainer maps to characters)."""
import sys, re
ascii_bdf, kana_bdf, kanji_bdf, out = sys.argv[1:5]

def glyphs(path):
    text = open(path, encoding='latin-1').read()
    header = text[:text.index('STARTCHAR')]
    for m in re.finditer(r'STARTCHAR .*?\nENCODING (-?\d+)\n(.*?)ENDCHAR\n', text, re.S):
        yield int(m.group(1)), m.group(2), header

uni = {}
def add(cp, body):
    if cp not in uni:
        uni[cp] = body

# ISO 8859-1: code == Unicode
for enc, body, _ in glyphs(ascii_bdf):
    if 0x20 <= enc <= 0xFF:
        add(enc, body)
# JIS X 0201 katakana 0xA1-0xDF -> half-width katakana U+FF61-U+FF9F
for enc, body, _ in glyphs(kana_bdf):
    if 0xA1 <= enc <= 0xDF:
        add(0xFF61 + enc - 0xA1, body)
# JIS X 0208 row/cell -> Unicode through EUC-JP; also the code points Java's MS932 uses
VARIANTS = {0x301C: [0xFF5E], 0x2016: [0x2225], 0x2212: [0xFF0D], 0x00A2: [0xFFE0], 0x00A3: [0xFFE1],
            0x00AC: [0xFFE2], 0x2014: [0x2015], 0x00A6: [0xFFE4]}
kanji_header = None
for enc, body, header in glyphs(kanji_bdf):
    kanji_header = header
    hi, lo = enc >> 8, enc & 0xFF
    try:
        ch = bytes([hi | 0x80, lo | 0x80]).decode('euc_jp')
    except UnicodeDecodeError:
        continue
    cp = ord(ch)
    add(cp, body)
    for v in VARIANTS.get(cp, []):
        add(v, body)

hdr = kanji_header
def sub(pattern, repl):
    global hdr
    hdr = re.sub(pattern, repl, hdr, count=1, flags=re.M)
sub(r'^STARTFONT .*\n', 'STARTFONT 2.1\nCOMMENT Shinonome 12-dot fonts (public domain, /efont/ project), merged and renamed to\nCOMMENT Unicode U+XXXX glyph names for MIDPath by components/fonts-ja/build-font.sh.\n')
sub(r'^FONT .*\n', 'FONT -Shinonome-Gothic-Medium-R-Normal--12-110-75-75-C-120-ISO10646-1\n')
sub(r'^CHARSET_REGISTRY .*\n', 'CHARSET_REGISTRY "ISO10646"\n')
sub(r'^CHARSET_ENCODING .*\n', 'CHARSET_ENCODING "1"\n')
sub(r'^DEFAULT_CHAR .*\n', 'DEFAULT_CHAR 32\n')
sub(r'^CHARS \d+\n', 'CHARS %d\n' % len(uni))
with open(out, 'w', encoding='latin-1', newline='\n') as f:
    f.write(hdr)
    for cp in sorted(uni):
        f.write('STARTCHAR U+%04X\nENCODING %d\n%sENDCHAR\n' % (cp, cp, uni[cp]))
    f.write('ENDFONT\n')
print(len(uni), 'glyphs ->', out)
