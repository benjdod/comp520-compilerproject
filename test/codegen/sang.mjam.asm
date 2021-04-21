  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L10
  3         HALT   (0)   
  4         PUSH         1
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L10:   PUSH         1
  9         LOADL        -1
 10         LOADL        1
 11         CALL         newobj  
 12         STORE        3[LB]
 13         LOAD         3[LB]
 14         LOADL        0
 15         LOADL        31
 16         CALL         fieldupd
 17         PUSH         1
 18         LOADL        -1
 19         LOADL        1
 20         CALL         newobj  
 21         STORE        4[LB]
 22         LOAD         4[LB]
 23         LOADL        0
 24         LOAD         3[LB]
 25         CALL         fieldupd
 26         PUSH         1
 27         LOADL        -1
 28         LOADL        1
 29         CALL         newobj  
 30         STORE        5[LB]
 31         LOAD         5[LB]
 32         LOADL        0
 33         LOAD         4[LB]
 34         CALL         fieldupd
 35         LOAD         -1[CB]
 36         CALLI        L11
 37         POP          0
 38         RETURN (0)   1
 39  L11:   LOAD         0[OB]
 40         RETURN (1)   0
