00002   01 MASTER_REC.                                                  COL 73-80
00003      05  ACCOUNT_NO                  PIC X(9).                    COL 73-80
00004      05  REC_TYPE                    PIC X.                       COL 73-80
00005      05  AMOUNT                      PIC S9(4)V99 COMP-3.         COL 73-80
00006      05  BIN-NO                      PIC S9(8) COMP.              COL 73-80
00007      05  BIN-NO-X REDEFINES BIN-NO   PIC XXXX.                    COL 73-80
00008      05  DECIMAL-NO                  PIC S999.                    COL 73-80
00009      05  MASTER-DATE.                                             COL 73-80
00010          10  DATE-YY            PIC 9(2).                         COL 73-80
00011          10  DATE-MM            PIC 9(2).                         COL 73-80
00012          10  DATE-DD            PIC 9(2).                         COL 73-80
00013      05  MASTER-DOB REDEFINES MASTER-DATE.                        COL 73-80
00014          10  YYMMDD             PIC XXXXXX.                       COL 73-80
00015      05  ACT_TYPE                    PIC X.                       COL 73-80 
00016      05  OTHER-DATE.                                              COL 73-80
00017          10  ODATE-YY           PIC 9(2).                         COL 73-80
00018          10  ODATE-MM           PIC 9(2).                         COL 73-80
00019          10  ODATE-DD           PIC 9(2).                         COL 73-80
00020      05  OTHER-DOB REDEFINES OTHER-DATE.                          COL 73-80
00021          10  OYYMMDDTT          PIC 9(8).                         COL 73-80
00022      05  OTHER_TYPE                  PIC X.                       COL 73-80
00023      