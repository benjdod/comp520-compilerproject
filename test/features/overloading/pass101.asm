  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   LOADL        1
  9         RETURN (1)   0
 10  L12:   LOADL        1
 11         LOAD         -1[LB]
 12         CALL         add     
 13         RETURN (1)   1
 14  L13:   PUSH         1
 15         LOADL        -1
 16         LOADL        0
 17         CALL         newobj  
 18         STORE        3[LB]
 19         LOAD         3[LB]
 20         CALLI        L14
 21         CALL         L11
 22         POP          1
 23         LOADL        5
 24         LOAD         3[LB]
 25         CALLI        L12
 26         POP          1
 27         LOADL        5
 28         LOAD         3[LB]
 29         CALLI        L12
 30         CALL         L10
 31         RETURN (0)   1
 32  L14:   LOADL        4
 33         LOADA        0[OB]
 34         CALLI        L12
 35         POP          1
 36         RETURN (0)   0
