  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L11
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   PUSH         1
 10         LOADL        -1
 11         LOADL        1
 12         CALL         newobj  
 13         STORE        3[LB]
 14         LOAD         3[LB]
 15         LOADL        0
 16         LOADL        18
 17         CALL         fieldupd
 18         PUSH         1
 19         LOADL        -1
 20         LOADL        1
 21         CALL         newobj  
 22         STORE        4[LB]
 23         LOAD         4[LB]
 24         LOADL        0
 25         LOAD         3[LB]
 26         CALL         fieldupd
 27         LOAD         1[SB]
 28         LOADL        1
 29         LOAD         4[LB]
 30         STORE        1[SB]
 31         LOAD         1[SB]
 32         LOAD         1[SB]
 33         LOADL        0
 34         CALL         fieldref
 35         LOADL        0
 36         CALL         fieldref
 37         LOADL        87
 38         CALL         add     
 39         CALL         L10
 40         RETURN (0)   1
