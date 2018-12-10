package wcbc;


/**
 * A helper struct used for returning values in certain programs like TspDir
 *
 */
class PathAndLen {
	Path path;
	int len;

	public PathAndLen(Path path, int len) {
		this.path = path;
		this.len = len;
	}
}

