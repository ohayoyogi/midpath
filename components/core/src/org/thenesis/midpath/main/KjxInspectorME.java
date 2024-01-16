package org.thenesis.midpath.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.thenesis.midpath.io.MemoryFileHandler;
import org.thenesis.midpath.zip.ZipEntry;
import org.thenesis.midpath.zip.ZipFile;

import com.sun.midp.io.IOToolkit;
import com.sun.midp.io.j2me.file.BaseFileHandler;

public class KjxInspectorME extends AbstractJarInspector {

    private String fileName;
    
    public KjxInspectorME(String fileName) {
        this.fileName = fileName;
    }

    byte readByte(BaseFileHandler fHandler) throws IOException {
        byte[] buff = new byte[1];
        fHandler.read(buff, 0, 1);
        return buff[0];
    }

    byte[] readBytes(BaseFileHandler fHandler, int length) throws IOException {
        byte[] buff = new byte[length];
        System.out.println("length: " + length);
        fHandler.read(buff, 0, length);
        return buff;
    }
 
    int readUnsignedShort(BaseFileHandler fHandler) throws IOException {
        byte[] buff = new byte[2];
        fHandler.read(buff, 0, 2);
        return (buff[0] & 0xFF) << 8 | (buff[1] & 0xFF);
    }

    public InputStream getManifest() throws IOException {
        BaseFileHandler fHandler = IOToolkit.getToolkit().createBaseFileHandler();
        fHandler.connect("", fileName);
        if (!fHandler.exists()) {
            throw new IOException("File doesn't exist");
        }

        fHandler.openForRead();
        // check magic bytes
        boolean valid = false;
        try {
            byte[] magicbuf = new byte[3];
            fHandler.readFully(magicbuf, 0, magicbuf.length);
            valid = (magicbuf[0] == 'K') && (magicbuf[1] == 'J') && (magicbuf[2] == 'X');
            System.out.println(new String(magicbuf));
            System.out.println(valid);
        } catch (IOException ex) {
        }
        if (!valid) {
            try {
                fHandler.close();
            } catch(IOException ex) {
            }
            throw new IOException("Not a valid kjx file");
        }

        int chunksize = 1024;
        byte[] buff = new byte[chunksize];
        System.out.println(fHandler.fileSize());
        byte posJadStart = readByte(fHandler);
        byte lenKjxFileName = readByte(fHandler);
        /*byte[] kjxFileName = */readBytes(fHandler, lenKjxFileName);
        int lenJadFileContent = readUnsignedShort(fHandler);
        byte lenJadFileName = readByte(fHandler);
        /*byte[] jarFileName = */readBytes(fHandler, lenJadFileName);
        /*byte[] jadContent = */readBytes(fHandler, lenJadFileContent);

        // Copy jar content on MemoryFileHandler
        MemoryFileHandler mFileHandler = new MemoryFileHandler();
        mFileHandler.connect("/", fileName);
        mFileHandler.create();
        mFileHandler.openForWrite();
        
        int readSize = 0;
        while((readSize = fHandler.read(buff, 0, chunksize)) > 0) {
            mFileHandler.write(buff, 0, readSize);
        }
        fHandler.close();

        mFileHandler.seek(0);

        ZipFile file = new ZipFile(mFileHandler);
        // Get the manifest zip entry 
		// ZipEntry entry = file.getEntry("META-INF/MANIFEST.MF");
		Enumeration enumeration = file.entries();
		ZipEntry manifestEntry = null;
		while(enumeration.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)enumeration.nextElement();
			if (entry.getName().equalsIgnoreCase("META-INF/MANIFEST.MF")) {
				manifestEntry = entry;
				break;
			}
		}
		
		if (manifestEntry == null) {
			throw new IOException("No MANIFEST file in the jar");
		}
		
		InputStream is = file.getInputStream(manifestEntry);
		return is;
    }
}
