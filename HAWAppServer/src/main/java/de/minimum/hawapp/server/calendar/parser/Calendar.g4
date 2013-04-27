/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Calendar;
options {
  language = Java;
}

@header {
  import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Locale;
}

prog:header terminBlock[$header.year]+ (header (tblock2=terminBlock[$header.year])+)*;

header returns[Integer year]:STUNDENPLAN WS semester WS VERSION WS versionNr WS VOM WS stand {$year=$stand.year;} WS* NEWLINE SEMESTERGRUPPE WS semgruppe WS* NEWLINE;

stundenplan: WORD;

semester:WORD WS WORD;

versionNr:WORD;

stand returns [Integer year]:yearString=WORD{$year=Integer.parseInt(($yearString.text.split("\\.")[2]));};

semgruppe:WORD;

terminBlock[Integer year]:weekList=weeks TERMINBLOCK_HEADER NEWLINE termine[$weeks.weekList, $year];

weeks returns [List<Integer> weekList]:{$weekList=new ArrayList<Integer>();} (week=WORD
              {if($week.text.contains("-")){
                    String[] splited=$week.text.split("-");
                    int end=Integer.parseInt(splited[1]);
                    for(int i=Integer.parseInt(splited[0]); i<=end ; i++){
			$weekList.add(i);
                    }
		}else{
               $weekList.add(Integer.parseInt($week.text));};}((KOMMA)WS* week=WORD { $weekList.add(Integer.parseInt($week.text));})*)NEWLINE ;

termine[List<Integer> wochen, Integer year]:(termin[$wochen, $year])+;

termin[List<Integer> wochen, Integer year]:WS* name WS* KOMMA WS* dozent WS* KOMMA WS* raum WS* KOMMA WS* tag KOMMA WS* anfang WS* KOMMA WS* ende WS* (NEWLINE|EOF)
    {
      //System.out.println("name: "+ $name.text + " dozent: " + $dozent.text + " raum: " + $raum.text + " begin: " + $anfang.text + " end: "+ $ende.text);
     };

name:(WORD (WS WORD)*)+;

dozent:(WORD (WS WORD)*)*;

raum:(WORD (WS WORD)*)*;

tag returns [Integer weekday]:day=DAY{
    Integer integer=null;
                    switch($day.text){
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
            $weekday=integer;
    };
anfang returns[Date begintime]:timeString=WORD 
{try{
    $begintime=DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse($timeString.text);
    }catch(ParseException e)
    {
    throw new RuntimeException(e);
    }
};
ende returns[Date endtime]:timeString=WORD {$endtime=null; try{$endtime=DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse($timeString.text);}catch(ParseException e){throw new RuntimeException(e);}};

VOM:'vom';
VERSION:'Vers';
STUNDENPLAN:'Stundenplan';
SEMESTERGRUPPE:'Semestergruppe';
TERMINBLOCK_HEADER:'Name,Dozent,Raum,Tag,Anfang,Ende';
KOMMA:',';
DAY:('Mo'|'Di'|'Mi'|'Do'|'Fr'|'Sa'|'So');
WORD:(~(' '|'\t'|'\n'|'\r'|',')|('0'..'9'))+;
NEWLINE: ('\n'|'\r')+;
WS : (' ')+;


