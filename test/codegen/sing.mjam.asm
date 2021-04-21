  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
  3         HALT   (0)   
  4         PUSH         1
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   PUSH         1
  9         LOADL        0
 10         STORE        3[LB]
 11         LOADL        -1
 12         LOADL        1
 13         CALL         newobj  
 14         STORE        3[LB]
 15         LOAD         3[LB]
 16         LOADL        0
 17         CALL         fieldref
 18         LOAD         5[CB]
 19         CALLI        L10
 20         RETURN (0)   1
