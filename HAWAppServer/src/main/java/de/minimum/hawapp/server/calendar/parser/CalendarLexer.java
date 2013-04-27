// Generated from C:\Users\jannik\eclipse_workspaces\android_project\HAWAppServer\src\main\java\de\minimum\hawapp\server\calendar\parser\Calendar.g4 by ANTLR 4.0
package de.minimum.hawapp.server.calendar.parser;

  import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Locale;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalendarLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VOM=1, VERSION=2, STUNDENPLAN=3, SEMESTERGRUPPE=4, TERMINBLOCK_HEADER=5, 
		KOMMA=6, DAY=7, WORD=8, NEWLINE=9, WS=10;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'vom'", "'Vers'", "'Stundenplan'", "'Semestergruppe'", "'Name,Dozent,Raum,Tag,Anfang,Ende'", 
		"','", "DAY", "WORD", "NEWLINE", "WS"
	};
	public static final String[] ruleNames = {
		"VOM", "VERSION", "STUNDENPLAN", "SEMESTERGRUPPE", "TERMINBLOCK_HEADER", 
		"KOMMA", "DAY", "WORD", "NEWLINE", "WS"
	};


	public CalendarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Calendar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\2\4\f~\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\5\bm\n\b\3\t\3\t\6\tq\n\t\r\t\16\tr\3\n\6\nv\n\n\r\n\16"+
		"\nw\3\13\6\13{\n\13\r\13\16\13|\2\f\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b"+
		"\1\17\t\1\21\n\1\23\13\1\25\f\1\3\2\4\6\13\f\17\17\"\"..\4\f\f\17\17\u0087"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2"+
		"\5\33\3\2\2\2\7 \3\2\2\2\t,\3\2\2\2\13;\3\2\2\2\r\\\3\2\2\2\17l\3\2\2"+
		"\2\21p\3\2\2\2\23u\3\2\2\2\25z\3\2\2\2\27\30\7x\2\2\30\31\7q\2\2\31\32"+
		"\7o\2\2\32\4\3\2\2\2\33\34\7X\2\2\34\35\7g\2\2\35\36\7t\2\2\36\37\7u\2"+
		"\2\37\6\3\2\2\2 !\7U\2\2!\"\7v\2\2\"#\7w\2\2#$\7p\2\2$%\7f\2\2%&\7g\2"+
		"\2&\'\7p\2\2\'(\7r\2\2()\7n\2\2)*\7c\2\2*+\7p\2\2+\b\3\2\2\2,-\7U\2\2"+
		"-.\7g\2\2./\7o\2\2/\60\7g\2\2\60\61\7u\2\2\61\62\7v\2\2\62\63\7g\2\2\63"+
		"\64\7t\2\2\64\65\7i\2\2\65\66\7t\2\2\66\67\7w\2\2\678\7r\2\289\7r\2\2"+
		"9:\7g\2\2:\n\3\2\2\2;<\7P\2\2<=\7c\2\2=>\7o\2\2>?\7g\2\2?@\7.\2\2@A\7"+
		"F\2\2AB\7q\2\2BC\7|\2\2CD\7g\2\2DE\7p\2\2EF\7v\2\2FG\7.\2\2GH\7T\2\2H"+
		"I\7c\2\2IJ\7w\2\2JK\7o\2\2KL\7.\2\2LM\7V\2\2MN\7c\2\2NO\7i\2\2OP\7.\2"+
		"\2PQ\7C\2\2QR\7p\2\2RS\7h\2\2ST\7c\2\2TU\7p\2\2UV\7i\2\2VW\7.\2\2WX\7"+
		"G\2\2XY\7p\2\2YZ\7f\2\2Z[\7g\2\2[\f\3\2\2\2\\]\7.\2\2]\16\3\2\2\2^_\7"+
		"O\2\2_m\7q\2\2`a\7F\2\2am\7k\2\2bc\7O\2\2cm\7k\2\2de\7F\2\2em\7q\2\2f"+
		"g\7H\2\2gm\7t\2\2hi\7U\2\2im\7c\2\2jk\7U\2\2km\7q\2\2l^\3\2\2\2l`\3\2"+
		"\2\2lb\3\2\2\2ld\3\2\2\2lf\3\2\2\2lh\3\2\2\2lj\3\2\2\2m\20\3\2\2\2nq\n"+
		"\2\2\2oq\4\62;\2pn\3\2\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\22"+
		"\3\2\2\2tv\t\3\2\2ut\3\2\2\2vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\24\3\2\2\2"+
		"y{\7\"\2\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\26\3\2\2\2\b\2lpr"+
		"w|";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}