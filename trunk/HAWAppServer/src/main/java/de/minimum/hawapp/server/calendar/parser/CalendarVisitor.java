// Generated from C:\Users\jannik\eclipse_workspaces\android_project\HAWAppServer\src\main\java\de\minimum\hawapp\server\calendar\parser\Calendar.g4 by ANTLR 4.0
package de.minimum.hawapp.server.calendar.parser;

  import org.antlr.v4.runtime.tree.*;

public interface CalendarVisitor<T> extends ParseTreeVisitor<T> {
	T visitAnfang(CalendarParser.AnfangContext ctx);

	T visitTag(CalendarParser.TagContext ctx);

	T visitDozent(CalendarParser.DozentContext ctx);

	T visitTerminBlock(CalendarParser.TerminBlockContext ctx);

	T visitRaum(CalendarParser.RaumContext ctx);

	T visitHeader(CalendarParser.HeaderContext ctx);

	T visitStundenplan(CalendarParser.StundenplanContext ctx);

	T visitWeeks(CalendarParser.WeeksContext ctx);

	T visitTermine(CalendarParser.TermineContext ctx);

	T visitEnde(CalendarParser.EndeContext ctx);

	T visitProg(CalendarParser.ProgContext ctx);

	T visitTermin(CalendarParser.TerminContext ctx);

	T visitStand(CalendarParser.StandContext ctx);

	T visitName(CalendarParser.NameContext ctx);

	T visitVersionNr(CalendarParser.VersionNrContext ctx);

	T visitSemester(CalendarParser.SemesterContext ctx);

	T visitSemgruppe(CalendarParser.SemgruppeContext ctx);
}