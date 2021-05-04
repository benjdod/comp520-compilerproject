  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   LOADA        0[OB]
  9         LOADL        0
 10         CALL         fieldref
 11         RETURN (1)   0
 12  L12:   LOADA        0[OB]
 13         LOADL        0
 14         CALL         fieldref
 15         LOAD         -1[LB]
 16         CALL         add     
 17         RETURN (1)   1
 18  L13:   PUSH         1
 19         LOADL        -1
 20         LOADL        1
 21         CALL         newobj  
 22         STORE        3[LB]
 23         LOAD         3[LB]
 24         LOADL        0
 25         LOADL        5
 26         CALL         fieldupd
 27         LOAD         3[LB]
 28         CALLI        L11
 29         LOADL        5
 30         CALL         neg     
 31         LOAD         3[LB]
 32         CALLI        L12
 33         CALL         add     
 34         CALL         L10
 35         RETURN (0)   1
