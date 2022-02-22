package client;

/**
 * displays the StoreClient message in a graphical window
 * rather than as text in the output console, as in the parent StoreClient
 */
public class StoreClientGraphic extends StoreClient{

	@Override
	public void displayResult() {
		new DisplayFrame(message);
	}
}
