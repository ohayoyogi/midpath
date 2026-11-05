# fonts-ja

Japanese bitmap font for MIDPath's BDF font renderer, built into `dist/fonts-ja.jar`.

`resources/fonts/shinonome12-unicode.bdf` merges the 12-dot
[Shinonome (東雲) fonts](http://openlab.ring.gr.jp/efont/shinonome/) by Yasuyuki Furukawa
and the /efont/ project - ISO 8859-1, JIS X 0201 half-width katakana and JIS X 0208,
7161 glyphs - into one BDF with Unicode glyph names (`U+XXXX`), as `BDFFontContainer`
expects. Regenerate it with `build-font.sh`.

## Use

Put `fonts-ja.jar` on the classpath and select the font in the configuration (or as Java
system properties, which take precedence):

```
org.thenesis.midpath.ui.fontRenderer: BDF
org.thenesis.midpath.font.bdf.filename: /fonts/shinonome12-unicode.bdf
```

Japanese MIDlets also commonly decode text with `new String(byte[])`, which phones did as
Shift_JIS. On Java SE that follows the VM's default charset, so start the VM with
`-Dfile.encoding=MS932` as well.

## License

The Shinonome fonts are in the public domain (the authors declare they will not exercise
their rights), and may be freely modified, converted, embedded and redistributed. The
scripts here are part of MIDPath (GNU GPL version 2).
