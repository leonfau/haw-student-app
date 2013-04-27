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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalendarParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VOM=1, VERSION=2, STUNDENPLAN=3, SEMESTERGRUPPE=4, TERMINBLOCK_HEADER=5, 
		KOMMA=6, DAY=7, WORD=8, NEWLINE=9, WS=10;
	public static final String[] tokenNames = {
		"<INVALID>", "'vom'", "'Vers'", "'Stundenplan'", "'Semestergruppe'", "'Name,Dozent,Raum,Tag,Anfang,Ende'", 
		"','", "DAY", "WORD", "NEWLINE", "WS"
	};
	public static final int
		RULE_prog = 0, RULE_header = 1, RULE_stundenplan = 2, RULE_semester = 3, 
		RULE_versionNr = 4, RULE_stand = 5, RULE_semgruppe = 6, RULE_terminBlock = 7, 
		RULE_weeks = 8, RULE_termine = 9, RULE_termin = 10, RULE_name = 11, RULE_dozent = 12, 
		RULE_raum = 13, RULE_tag = 14, RULE_anfang = 15, RULE_ende = 16;
	public static final String[] ruleNames = {
		"prog", "header", "stundenplan", "semester", "versionNr", "stand", "semgruppe", 
		"terminBlock", "weeks", "termine", "termin", "name", "dozent", "raum", 
		"tag", "anfang", "ende"
	};

	@Override
	public String getGrammarFileName() { return "Calendar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public CalendarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public HeaderContext header;
		public TerminBlockContext tblock2;
		public TerminBlockContext terminBlock(int i) {
			return getRuleContext(TerminBlockContext.class,i);
		}
		public List<TerminBlockContext> terminBlock() {
			return getRuleContexts(TerminBlockContext.class);
		}
		public HeaderContext header(int i) {
			return getRuleContext(HeaderContext.class,i);
		}
		public List<HeaderContext> header() {
			return getRuleContexts(HeaderContext.class);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34); ((ProgContext)_localctx).header = header();
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35); terminBlock(((ProgContext)_localctx).header.year);
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WORD );
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STUNDENPLAN) {
				{
				{
				setState(40); ((ProgContext)_localctx).header = header();
				setState(42); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(41); ((ProgContext)_localctx).tblock2 = terminBlock(((ProgContext)_localctx).header.year);
					}
					}
					setState(44); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WORD );
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeaderContext extends ParserRuleContext {
		public Integer year;
		public StandContext stand;
		public TerminalNode VOM() { return getToken(CalendarParser.VOM, 0); }
		public TerminalNode VERSION() { return getToken(CalendarParser.VERSION, 0); }
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public StandContext stand() {
			return getRuleContext(StandContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(CalendarParser.NEWLINE); }
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public TerminalNode SEMESTERGRUPPE() { return getToken(CalendarParser.SEMESTERGRUPPE, 0); }
		public TerminalNode NEWLINE(int i) {
			return getToken(CalendarParser.NEWLINE, i);
		}
		public TerminalNode STUNDENPLAN() { return getToken(CalendarParser.STUNDENPLAN, 0); }
		public VersionNrContext versionNr() {
			return getRuleContext(VersionNrContext.class,0);
		}
		public SemgruppeContext semgruppe() {
			return getRuleContext(SemgruppeContext.class,0);
		}
		public SemesterContext semester() {
			return getRuleContext(SemesterContext.class,0);
		}
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_header);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); match(STUNDENPLAN);
			setState(52); match(WS);
			setState(53); semester();
			setState(54); match(WS);
			setState(55); match(VERSION);
			setState(56); match(WS);
			setState(57); versionNr();
			setState(58); match(WS);
			setState(59); match(VOM);
			setState(60); match(WS);
			setState(61); ((HeaderContext)_localctx).stand = stand();
			((HeaderContext)_localctx).year = ((HeaderContext)_localctx).stand.year;
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(63); match(WS);
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69); match(NEWLINE);
			setState(70); match(SEMESTERGRUPPE);
			setState(71); match(WS);
			setState(72); semgruppe();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(73); match(WS);
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79); match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StundenplanContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public StundenplanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stundenplan; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitStundenplan(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StundenplanContext stundenplan() throws RecognitionException {
		StundenplanContext _localctx = new StundenplanContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stundenplan);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SemesterContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(CalendarParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(CalendarParser.WORD, i);
		}
		public TerminalNode WS() { return getToken(CalendarParser.WS, 0); }
		public SemesterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semester; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitSemester(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SemesterContext semester() throws RecognitionException {
		SemesterContext _localctx = new SemesterContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_semester);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83); match(WORD);
			setState(84); match(WS);
			setState(85); match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionNrContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public VersionNrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versionNr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitVersionNr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VersionNrContext versionNr() throws RecognitionException {
		VersionNrContext _localctx = new VersionNrContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_versionNr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StandContext extends ParserRuleContext {
		public Integer year;
		public Token yearString;
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public StandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stand; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitStand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StandContext stand() throws RecognitionException {
		StandContext _localctx = new StandContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_stand);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); ((StandContext)_localctx).yearString = match(WORD);
			((StandContext)_localctx).year = Integer.parseInt(((((StandContext)_localctx).yearString!=null?((StandContext)_localctx).yearString.getText():null).split("\\.")[2]));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SemgruppeContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public SemgruppeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_semgruppe; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitSemgruppe(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SemgruppeContext semgruppe() throws RecognitionException {
		SemgruppeContext _localctx = new SemgruppeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_semgruppe);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminBlockContext extends ParserRuleContext {
		public Integer year;
		public WeeksContext weekList;
		public WeeksContext weeks;
		public TerminalNode NEWLINE() { return getToken(CalendarParser.NEWLINE, 0); }
		public TerminalNode TERMINBLOCK_HEADER() { return getToken(CalendarParser.TERMINBLOCK_HEADER, 0); }
		public WeeksContext weeks() {
			return getRuleContext(WeeksContext.class,0);
		}
		public TermineContext termine() {
			return getRuleContext(TermineContext.class,0);
		}
		public TerminBlockContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TerminBlockContext(ParserRuleContext parent, int invokingState, Integer year) {
			super(parent, invokingState);
			this.year = year;
		}
		@Override public int getRuleIndex() { return RULE_terminBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitTerminBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminBlockContext terminBlock(Integer year) throws RecognitionException {
		TerminBlockContext _localctx = new TerminBlockContext(_ctx, getState(), year);
		enterRule(_localctx, 14, RULE_terminBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94); ((TerminBlockContext)_localctx).weekList = ((TerminBlockContext)_localctx).weeks = weeks();
			setState(95); match(TERMINBLOCK_HEADER);
			setState(96); match(NEWLINE);
			setState(97); termine(((TerminBlockContext)_localctx).weeks.weekList, _localctx.year);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeeksContext extends ParserRuleContext {
		public List<Integer> weekList;
		public Token week;
		public List<TerminalNode> WORD() { return getTokens(CalendarParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(CalendarParser.WORD, i);
		}
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public TerminalNode NEWLINE() { return getToken(CalendarParser.NEWLINE, 0); }
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public List<TerminalNode> KOMMA() { return getTokens(CalendarParser.KOMMA); }
		public TerminalNode KOMMA(int i) {
			return getToken(CalendarParser.KOMMA, i);
		}
		public WeeksContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weeks; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitWeeks(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeeksContext weeks() throws RecognitionException {
		WeeksContext _localctx = new WeeksContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_weeks);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			((WeeksContext)_localctx).weekList = new ArrayList<Integer>();
			{
			setState(100); ((WeeksContext)_localctx).week = match(WORD);
			if((((WeeksContext)_localctx).week!=null?((WeeksContext)_localctx).week.getText():null).contains("-")){
			                    String[] splited=(((WeeksContext)_localctx).week!=null?((WeeksContext)_localctx).week.getText():null).split("-");
			                    int end=Integer.parseInt(splited[1]);
			                    for(int i=Integer.parseInt(splited[0]); i<=end ; i++){
						_localctx.weekList.add(i);
			                    }
					}else{
			               _localctx.weekList.add(Integer.parseInt((((WeeksContext)_localctx).week!=null?((WeeksContext)_localctx).week.getText():null)));};
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KOMMA) {
				{
				{
				{
				setState(102); match(KOMMA);
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(103); match(WS);
					}
					}
					setState(108);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(109); ((WeeksContext)_localctx).week = match(WORD);
				 _localctx.weekList.add(Integer.parseInt((((WeeksContext)_localctx).week!=null?((WeeksContext)_localctx).week.getText():null)));
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(116); match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermineContext extends ParserRuleContext {
		public List<Integer> wochen;
		public Integer year;
		public List<TerminContext> termin() {
			return getRuleContexts(TerminContext.class);
		}
		public TerminContext termin(int i) {
			return getRuleContext(TerminContext.class,i);
		}
		public TermineContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TermineContext(ParserRuleContext parent, int invokingState, List<Integer> wochen, Integer year) {
			super(parent, invokingState);
			this.wochen = wochen;
			this.year = year;
		}
		@Override public int getRuleIndex() { return RULE_termine; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitTermine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermineContext termine(List<Integer> wochen,Integer year) throws RecognitionException {
		TermineContext _localctx = new TermineContext(_ctx, getState(), wochen, year);
		enterRule(_localctx, 18, RULE_termine);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(119); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(118); termin(_localctx.wochen, _localctx.year);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(121); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			} while ( _alt!=2 && _alt!=-1 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminContext extends ParserRuleContext {
		public List<Integer> wochen;
		public Integer year;
		public NameContext name;
		public DozentContext dozent;
		public RaumContext raum;
		public AnfangContext anfang;
		public EndeContext ende;
		public EndeContext ende() {
			return getRuleContext(EndeContext.class,0);
		}
		public AnfangContext anfang() {
			return getRuleContext(AnfangContext.class,0);
		}
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public TerminalNode NEWLINE() { return getToken(CalendarParser.NEWLINE, 0); }
		public TagContext tag() {
			return getRuleContext(TagContext.class,0);
		}
		public DozentContext dozent() {
			return getRuleContext(DozentContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public TerminalNode EOF() { return getToken(CalendarParser.EOF, 0); }
		public RaumContext raum() {
			return getRuleContext(RaumContext.class,0);
		}
		public List<TerminalNode> KOMMA() { return getTokens(CalendarParser.KOMMA); }
		public TerminalNode KOMMA(int i) {
			return getToken(CalendarParser.KOMMA, i);
		}
		public TerminContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TerminContext(ParserRuleContext parent, int invokingState, List<Integer> wochen, Integer year) {
			super(parent, invokingState);
			this.wochen = wochen;
			this.year = year;
		}
		@Override public int getRuleIndex() { return RULE_termin; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitTermin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminContext termin(List<Integer> wochen,Integer year) throws RecognitionException {
		TerminContext _localctx = new TerminContext(_ctx, getState(), wochen, year);
		enterRule(_localctx, 20, RULE_termin);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(123); match(WS);
				}
				}
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(129); ((TerminContext)_localctx).name = name();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(130); match(WS);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136); match(KOMMA);
			setState(140);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(137); match(WS);
					}
					} 
				}
				setState(142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(143); ((TerminContext)_localctx).dozent = dozent();
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(144); match(WS);
				}
				}
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(150); match(KOMMA);
			setState(154);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(151); match(WS);
					}
					} 
				}
				setState(156);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(157); ((TerminContext)_localctx).raum = raum();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(158); match(WS);
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(164); match(KOMMA);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(165); match(WS);
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171); tag();
			setState(172); match(KOMMA);
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(173); match(WS);
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(179); ((TerminContext)_localctx).anfang = anfang();
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(180); match(WS);
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(186); match(KOMMA);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(187); match(WS);
				}
				}
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(193); ((TerminContext)_localctx).ende = ende();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(194); match(WS);
				}
				}
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(200);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==NEWLINE) ) {
			_errHandler.recoverInline(this);
			}
			consume();

			      //System.out.println("name: "+ (((TerminContext)_localctx).name!=null?_input.getText(((TerminContext)_localctx).name.start,((TerminContext)_localctx).name.stop):null) + " dozent: " + (((TerminContext)_localctx).dozent!=null?_input.getText(((TerminContext)_localctx).dozent.start,((TerminContext)_localctx).dozent.stop):null) + " raum: " + (((TerminContext)_localctx).raum!=null?_input.getText(((TerminContext)_localctx).raum.start,((TerminContext)_localctx).raum.stop):null) + " begin: " + (((TerminContext)_localctx).anfang!=null?_input.getText(((TerminContext)_localctx).anfang.start,((TerminContext)_localctx).anfang.stop):null) + " end: "+ (((TerminContext)_localctx).ende!=null?_input.getText(((TerminContext)_localctx).ende.start,((TerminContext)_localctx).ende.stop):null));
			     
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(CalendarParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(CalendarParser.WORD, i);
		}
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_name);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(211); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(203); match(WORD);
				setState(208);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(204); match(WS);
						setState(205); match(WORD);
						}
						} 
					}
					setState(210);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
				}
				}
				}
				setState(213); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==WORD );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DozentContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(CalendarParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(CalendarParser.WORD, i);
		}
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public DozentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dozent; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitDozent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DozentContext dozent() throws RecognitionException {
		DozentContext _localctx = new DozentContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_dozent);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WORD) {
				{
				{
				setState(215); match(WORD);
				setState(220);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(216); match(WS);
						setState(217); match(WORD);
						}
						} 
					}
					setState(222);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				}
				}
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RaumContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(CalendarParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(CalendarParser.WORD, i);
		}
		public List<TerminalNode> WS() { return getTokens(CalendarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(CalendarParser.WS, i);
		}
		public RaumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_raum; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitRaum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RaumContext raum() throws RecognitionException {
		RaumContext _localctx = new RaumContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_raum);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WORD) {
				{
				{
				setState(228); match(WORD);
				setState(233);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(229); match(WS);
						setState(230); match(WORD);
						}
						} 
					}
					setState(235);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				}
				}
				}
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TagContext extends ParserRuleContext {
		public Integer weekday;
		public Token day;
		public TerminalNode DAY() { return getToken(CalendarParser.DAY, 0); }
		public TagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagContext tag() throws RecognitionException {
		TagContext _localctx = new TagContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_tag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241); ((TagContext)_localctx).day = match(DAY);

			    Integer integer=null;
			                    switch((((TagContext)_localctx).day!=null?((TagContext)_localctx).day.getText():null)){
			                    case "Mo":
			                            integer=Calendar.MONDAY;
			                            break;
			                    case "Di":
			                            integer=Calendar.TUESDAY;
			                            break;
			                    case "Mi":
			                            integer=Calendar.WEDNESDAY;
			                            break;
			                    case "Do":
			                            integer=Calendar.THURSDAY;
			                            break;
			                    case "Fr":
			                            integer=Calendar.FRIDAY;
			                            break;
			                    case "Sa":
			                            integer=Calendar.SATURDAY;
			                            break;
			                    case "So":
			                            integer=Calendar.SUNDAY;
			                            break;
			                    }
			            ((TagContext)_localctx).weekday = integer;
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnfangContext extends ParserRuleContext {
		public Date begintime;
		public Token timeString;
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public AnfangContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anfang; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitAnfang(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnfangContext anfang() throws RecognitionException {
		AnfangContext _localctx = new AnfangContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_anfang);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244); ((AnfangContext)_localctx).timeString = match(WORD);
			try{
			    ((AnfangContext)_localctx).begintime = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse((((AnfangContext)_localctx).timeString!=null?((AnfangContext)_localctx).timeString.getText():null));
			    }catch(ParseException e)
			    {
			    throw new RuntimeException(e);
			    }

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EndeContext extends ParserRuleContext {
		public Date endtime;
		public Token timeString;
		public TerminalNode WORD() { return getToken(CalendarParser.WORD, 0); }
		public EndeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ende; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalendarVisitor ) return ((CalendarVisitor<? extends T>)visitor).visitEnde(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndeContext ende() throws RecognitionException {
		EndeContext _localctx = new EndeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ende);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247); ((EndeContext)_localctx).timeString = match(WORD);
			((EndeContext)_localctx).endtime = null; try{((EndeContext)_localctx).endtime = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse((((EndeContext)_localctx).timeString!=null?((EndeContext)_localctx).timeString.getText():null));}catch(ParseException e){throw new RuntimeException(e);}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\f\u00fd\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\3\2\3\2\6\2\'\n\2\r\2\16\2(\3\2\3\2\6\2-\n\2\r\2"+
		"\16\2.\7\2\61\n\2\f\2\16\2\64\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\7\3C\n\3\f\3\16\3F\13\3\3\3\3\3\3\3\3\3\3\3\7\3M\n\3"+
		"\f\3\16\3P\13\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\7\nk\n\n\f\n\16\nn\13\n"+
		"\3\n\3\n\7\nr\n\n\f\n\16\nu\13\n\3\n\3\n\3\13\6\13z\n\13\r\13\16\13{\3"+
		"\f\7\f\177\n\f\f\f\16\f\u0082\13\f\3\f\3\f\7\f\u0086\n\f\f\f\16\f\u0089"+
		"\13\f\3\f\3\f\7\f\u008d\n\f\f\f\16\f\u0090\13\f\3\f\3\f\7\f\u0094\n\f"+
		"\f\f\16\f\u0097\13\f\3\f\3\f\7\f\u009b\n\f\f\f\16\f\u009e\13\f\3\f\3\f"+
		"\7\f\u00a2\n\f\f\f\16\f\u00a5\13\f\3\f\3\f\7\f\u00a9\n\f\f\f\16\f\u00ac"+
		"\13\f\3\f\3\f\3\f\7\f\u00b1\n\f\f\f\16\f\u00b4\13\f\3\f\3\f\7\f\u00b8"+
		"\n\f\f\f\16\f\u00bb\13\f\3\f\3\f\7\f\u00bf\n\f\f\f\16\f\u00c2\13\f\3\f"+
		"\3\f\7\f\u00c6\n\f\f\f\16\f\u00c9\13\f\3\f\3\f\3\f\3\r\3\r\3\r\7\r\u00d1"+
		"\n\r\f\r\16\r\u00d4\13\r\6\r\u00d6\n\r\r\r\16\r\u00d7\3\16\3\16\3\16\7"+
		"\16\u00dd\n\16\f\16\16\16\u00e0\13\16\7\16\u00e2\n\16\f\16\16\16\u00e5"+
		"\13\16\3\17\3\17\3\17\7\17\u00ea\n\17\f\17\16\17\u00ed\13\17\7\17\u00ef"+
		"\n\17\f\17\16\17\u00f2\13\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3"+
		"\22\3\22\2\23\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"\2\3\4\1\1\13\13"+
		"\u0104\2$\3\2\2\2\4\65\3\2\2\2\6S\3\2\2\2\bU\3\2\2\2\nY\3\2\2\2\f[\3\2"+
		"\2\2\16^\3\2\2\2\20`\3\2\2\2\22e\3\2\2\2\24y\3\2\2\2\26\u0080\3\2\2\2"+
		"\30\u00d5\3\2\2\2\32\u00e3\3\2\2\2\34\u00f0\3\2\2\2\36\u00f3\3\2\2\2 "+
		"\u00f6\3\2\2\2\"\u00f9\3\2\2\2$&\5\4\3\2%\'\5\20\t\2&%\3\2\2\2\'(\3\2"+
		"\2\2(&\3\2\2\2()\3\2\2\2)\62\3\2\2\2*,\5\4\3\2+-\5\20\t\2,+\3\2\2\2-."+
		"\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60*\3\2\2\2\61\64\3\2\2\2\62"+
		"\60\3\2\2\2\62\63\3\2\2\2\63\3\3\2\2\2\64\62\3\2\2\2\65\66\7\5\2\2\66"+
		"\67\7\f\2\2\678\5\b\5\289\7\f\2\29:\7\4\2\2:;\7\f\2\2;<\5\n\6\2<=\7\f"+
		"\2\2=>\7\3\2\2>?\7\f\2\2?@\5\f\7\2@D\b\3\1\2AC\7\f\2\2BA\3\2\2\2CF\3\2"+
		"\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\13\2\2HI\7\6\2\2IJ\7"+
		"\f\2\2JN\5\16\b\2KM\7\f\2\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OQ"+
		"\3\2\2\2PN\3\2\2\2QR\7\13\2\2R\5\3\2\2\2ST\7\n\2\2T\7\3\2\2\2UV\7\n\2"+
		"\2VW\7\f\2\2WX\7\n\2\2X\t\3\2\2\2YZ\7\n\2\2Z\13\3\2\2\2[\\\7\n\2\2\\]"+
		"\b\7\1\2]\r\3\2\2\2^_\7\n\2\2_\17\3\2\2\2`a\5\22\n\2ab\7\7\2\2bc\7\13"+
		"\2\2cd\5\24\13\2d\21\3\2\2\2ef\b\n\1\2fg\7\n\2\2gs\b\n\1\2hl\7\b\2\2i"+
		"k\7\f\2\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2mo\3\2\2\2nl\3\2\2\2"+
		"op\7\n\2\2pr\b\n\1\2qh\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tv\3\2\2\2"+
		"us\3\2\2\2vw\7\13\2\2w\23\3\2\2\2xz\5\26\f\2yx\3\2\2\2z{\3\2\2\2{y\3\2"+
		"\2\2{|\3\2\2\2|\25\3\2\2\2}\177\7\f\2\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080"+
		"~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2"+
		"\u0083\u0087\5\30\r\2\u0084\u0086\7\f\2\2\u0085\u0084\3\2\2\2\u0086\u0089"+
		"\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a\3\2\2\2\u0089"+
		"\u0087\3\2\2\2\u008a\u008e\7\b\2\2\u008b\u008d\7\f\2\2\u008c\u008b\3\2"+
		"\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f"+
		"\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0095\5\32\16\2\u0092\u0094\7"+
		"\f\2\2\u0093\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095"+
		"\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u009c\7\b"+
		"\2\2\u0099\u009b\7\f\2\2\u009a\u0099\3\2\2\2\u009b\u009e\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2\2\2\u009e\u009c\3\2"+
		"\2\2\u009f\u00a3\5\34\17\2\u00a0\u00a2\7\f\2\2\u00a1\u00a0\3\2\2\2\u00a2"+
		"\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\3\2"+
		"\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00aa\7\b\2\2\u00a7\u00a9\7\f\2\2\u00a8"+
		"\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2"+
		"\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\5\36\20\2\u00ae"+
		"\u00b2\7\b\2\2\u00af\u00b1\7\f\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2"+
		"\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b5\u00b9\5 \21\2\u00b6\u00b8\7\f\2\2\u00b7\u00b6\3\2"+
		"\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00bc\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00c0\7\b\2\2\u00bd\u00bf\7\f"+
		"\2\2\u00be\u00bd\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1\u00c3\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c7\5\""+
		"\22\2\u00c4\u00c6\7\f\2\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00ca\u00cb\t\2\2\2\u00cb\u00cc\b\f\1\2\u00cc\27\3\2\2\2\u00cd\u00d2"+
		"\7\n\2\2\u00ce\u00cf\7\f\2\2\u00cf\u00d1\7\n\2\2\u00d0\u00ce\3\2\2\2\u00d1"+
		"\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d6\3\2"+
		"\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00cd\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		"\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\31\3\2\2\2\u00d9\u00de\7\n\2"+
		"\2\u00da\u00db\7\f\2\2\u00db\u00dd\7\n\2\2\u00dc\u00da\3\2\2\2\u00dd\u00e0"+
		"\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0"+
		"\u00de\3\2\2\2\u00e1\u00d9\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e1\3\2"+
		"\2\2\u00e3\u00e4\3\2\2\2\u00e4\33\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00eb"+
		"\7\n\2\2\u00e7\u00e8\7\f\2\2\u00e8\u00ea\7\n\2\2\u00e9\u00e7\3\2\2\2\u00ea"+
		"\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ef\3\2"+
		"\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00e6\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0"+
		"\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\35\3\2\2\2\u00f2\u00f0\3\2\2"+
		"\2\u00f3\u00f4\7\t\2\2\u00f4\u00f5\b\20\1\2\u00f5\37\3\2\2\2\u00f6\u00f7"+
		"\7\n\2\2\u00f7\u00f8\b\21\1\2\u00f8!\3\2\2\2\u00f9\u00fa\7\n\2\2\u00fa"+
		"\u00fb\b\22\1\2\u00fb#\3\2\2\2\33(.\62DNls{\u0080\u0087\u008e\u0095\u009c"+
		"\u00a3\u00aa\u00b2\u00b9\u00c0\u00c7\u00d2\u00d7\u00de\u00e3\u00eb\u00f0";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}