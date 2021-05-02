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
 14  L13:   CALL         L11
 15         CALL         L10
 16         LOADL        1
 17         CALL         L12
 18         CALL         L10
 19         RETURN (0)   1
