package org.ohayoyogi.media;

public class StubMediaPlayer implements BaseMediaPlayer {

	// @Override
	public void play(int count) {
		System.out.println("Stub: method 'play'");
	}

	// @Override
	public void stop() {
		System.out.println("Stub: method 'stop'");
	}

	// @Override
	public void pause() {
		System.out.println("Stub: method 'pause'");
	}

	// @Override
	public void resume() {
		System.out.println("Stub: method 'resume'");
	}

	// @Override
	public void setResource(int resourceId) {
		System.out.println("Stub: method 'setResource'");
	}

	// @Override
	public void unsetResource(int resourceId) {
		System.out.println("Stub: method 'unsetResource'");
	}
}
