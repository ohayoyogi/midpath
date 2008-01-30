package javax.microedition.m3g;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.lcdui.Image;

public class Image2D extends Object3D {
	public static final int ALPHA = 96;
	public static final int LUMINANCE = 97;
	public static final int LUMINANCE_ALPHA = 98;
	public static final int RGB = 99;
	public static final int RGBA = 100;

	private int format;
	private boolean isMutable;
	private int width;
	private int height;
	private ByteBuffer pixels;

	public Image2D(int format, int width, int height) {
		this.isMutable = true;
		this.format = format;
		this.width = width;
		this.height = height;
	}

	public Image2D(int format, int width, int height, byte[] image) {
		this.isMutable = false;
		this.format = format;
		this.width = width;
		this.height = height;

		int bpp = getBytesPerPixel();

		if (image.length < width * height * bpp)
			throw new IllegalArgumentException("image.length != width*height");

		pixels = ByteBuffer.allocateDirect(width * height * bpp);
		pixels.put(image, 0, width * height * bpp);
		pixels.flip();
	}

	public Image2D(int format, int width, int height, byte[] image, byte[] palette) {
		this.isMutable = false;
		this.format = format;
		this.width = width;
		this.height = height;

		if (image.length < width * height)
			throw new IllegalArgumentException("image.length != width*height");

		int bytesPerPixel = getBytesPerPixel();
		pixels = ByteBuffer.allocateDirect(width * height * bytesPerPixel);
		for (int i = 0; i < width * height; ++i) {
			for (int c = 0; c < bytesPerPixel; ++c) {
				int index = ((int) image[i] & 0xFF) * bytesPerPixel + c;
				pixels.put(palette[index]);
			}
		}
		pixels.flip();
	}

	public Image2D(int format, Object image) {
		this.isMutable = false;
		this.format = format;

		if (image instanceof Image) {
			loadFromImage((Image) image);
		} else {
			throw new IllegalArgumentException("Unrecognized image object.");
		}

		//		else if(image instanceof String)
		//		{
		//			Image img = null;
		//			try {
		//				img = ImageIO.read(getClass().getResourceAsStream((String)image));
		//			} catch (IOException  e) {
		//			}
		//
		//			if (img != null)
		//				loadFromImage(img);
		//		}
		//		else
		//			throw new IllegalArgumentException("Unrecognized image object.");
	}

	public void set(int x, int y, int width, int height, byte[] image) {
		// TODO
	}

	private void loadFromImage(Image image) {
		this.width = image.getWidth();
		this.height = image.getHeight();

		System.out.println("Image2D.loadFromImage() 1");
		
		if (width == -1 || height == -1)
			throw new IllegalArgumentException("Failed to get width/height.");

		//BufferedImage img = (BufferedImage)image;

		int[] packedPixels = new int[width * height];
		image.getRGB(packedPixels, 0, width, 0, 0, width, height);
		
		System.out.println("Image2D.loadFromImage() 2");

		//        PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, width, height, packedPixels, 0, width);
		//        try {
		//            pixelgrabber.grabPixels();
		//        } catch (InterruptedException e) {
		//            throw new RuntimeException();
		//        }

		int bpp = getBytesPerPixel();
		pixels = ByteBuffer.allocateDirect(packedPixels.length * bpp);
		
		System.out.println("Image2D.loadFromImage() 2.1");

		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				int packedPixel = packedPixels[row * width + col];
				if (bpp == 1)
					pixels.put((byte) ((packedPixel >> 24) & 0xFF));
				else if (bpp == 2) {
					// TODO: what to do?
				} else if (bpp >= 3) {
					//System.out.println("Image2D.loadFromImage(): put");
					pixels.put((byte) ((packedPixel >> 16) & 0xFF));
					pixels.put((byte) ((packedPixel >> 8) & 0xFF));
					pixels.put((byte) ((packedPixel >> 0) & 0xFF));
					if (bpp >= 4)
						pixels.put((byte) ((packedPixel >> 24) & 0xFF));
				}
			}
		}
		System.out.println("Image2D.loadFromImage() 2.2");
		pixels.flip();
		
		System.out.println("Image2D.loadFromImage() 3");
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public boolean isMutable() {
		return isMutable;
	}

	public int getFormat() {
		return format;
	}

	public ByteBuffer getPixels() {
		return pixels;
	}

	int getBytesPerPixel() {
		if (format == RGBA)
			return 4;
		else if (format == RGB)
			return 3;
		else if (format == LUMINANCE_ALPHA)
			return 2;
		else
			return 1;
	}

	int getGLFormat() {
		if (format == RGBA)
			return GL10.GL_RGBA;
		else if (format == RGB)
			return GL10.GL_RGB;
		else if (format == LUMINANCE_ALPHA)
			return GL10.GL_LUMINANCE_ALPHA;
		else if (format == LUMINANCE)
			return GL10.GL_LUMINANCE;
		else if (format == ALPHA)
			return GL10.GL_ALPHA;
		throw new RuntimeException("Invalid format on image");
	}
}
