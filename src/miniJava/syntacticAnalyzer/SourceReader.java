package miniJava.syntacticAnalyzer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SourceReader extends Reader {

    private Reader _reader;
    private StringBuilder _buffer;
    private int _index;
    private int _line;
    private int _column;
    private int _lines_stored;
    private boolean _ready;

    private static final int LINES_TO_STORE = 5;

    public SourceReader(Reader r) {
        this._reader = r;
        this._buffer = new StringBuilder();
        this._index = 0;
        this._line = 1;
        this._column = 1;
        this._ready = false;
        this._lines_stored = 0;
    }

    public SourceReader(String s) {
        this(new StringReader(s));
    }

    private char currentChar() {
        try {
            return _buffer.charAt(_index);
        } catch (IndexOutOfBoundsException e) {return '\0';}
    }

    private char nextChar() {
        try {
            return _buffer.charAt(_index + 1);
        } catch (IndexOutOfBoundsException e) {return '\0';}
    }
    
    private void clearBufferFirstLine() {
    	int i = 0;
    	
    	while (_buffer.charAt(i++) != '\n') { ; }
		_buffer.delete(0, i);
		
		_index -= i;

    }

    private void readLineIntoBuffer() throws IOException, SourceError {
        int c = _reader.read();
        do {
            if (c > 127) {
                throw new SourceError(String.format("Bad encoding on character %c", c), 
                    "Note that ASCII is the only valid encoding for a MiniJava source file.",
                    new SourceMark(0,0));
            } else if (c == -1) ; 
            else _buffer.append((char) c);
        } while ((c = _reader.read()) != '\n' && c != '\0'  && c != -1);

        if (c == '\n') _buffer.append('\n');

        _lines_stored++;

        if (_lines_stored > LINES_TO_STORE) {
            clearBufferFirstLine();
            _lines_stored--;
        }        
    }

    public int read() throws IOException {

        if (!_ready) {
            readLineIntoBuffer();
            _ready = true;
        }

        if (currentChar() == '\n') {
            readLineIntoBuffer();
            _line++;
            _column = 1;
        } else if (currentChar() == '\0') {
            return '\0';
        } else {
            _column++;
        }
        int out = currentChar();
        _index++;
        return out;
    }   

    public SourceMark getMark() {
        return new SourceMark(_line, _column);
    }

    public int read(char[] buf, int off, int len) throws IOException {

        return _reader.read(buf, off, len);
    }

    public void close() throws IOException {
        _reader.close();
    }
    
    public String getBufferLines() {
    	return _buffer.toString();
    }
    
}
