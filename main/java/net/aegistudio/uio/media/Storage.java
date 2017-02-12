package net.aegistudio.uio.media;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>Represents a storage media in abstract. A storage media 
 * maybe either a directory or a normal file.</p>
 * 
 * <p>A directory can be a holder of sub-directories and files.
 * So for a directory behavior, it could:</p>
 * <ul>
 * 	<li>open a sub-directory. Via <i>open</i> method.
 * 	<li>create a normal file. Via <i>create</i> method.
 * 	<li>create a sub-directory. Via <i>mkdir</i> method.
 * 	<li>list the underlying files. Via <i>list</i> method.
 * 	<li> When erase on a folder, it will erase all files 
 * under it. Via <i>erase</i> method.
 * </ul>
 * 
 * <p>A normal file is the actually stored data. It could:
 * <ul>
 * 	<li>be read from. Via <i>read</i> method.
 * 	<li>be written to. Via <i>write</i> method.
 * 	<li>be erased. Via <i>erase</i> method.
 * </ul>
 * 
 * @author aegistudio
 */

public interface Storage {
	public Storage open(String path) throws IOException;
	
	public Storage create(String path) throws IOException;
	
	public Storage mkdir(String path) throws IOException;
	
	public String name();
	
	public InputStream read() throws IOException;
	
	public OutputStream write() throws IOException;
	
	public Storage[] list() throws IOException;
	
	public void erase() throws IOException;
}
