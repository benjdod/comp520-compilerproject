  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L11
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   CALL         L15
  9         LOADL        1
 10         LOADL        15
 11         CALL         L12
 12         POP          1
 13         LOADL        2
 14         CALL         L14
 15         POP          1
 16         LOADL        1
 17         LOADL        1
 18         CALL         L12
 19         CALL         L10
 20         RETURN (0)   1
 21  L12:   LOAD         -2[LB]
 22         JUMPIF (0)   L13
 23         LOAD         -1[LB]
 24         CALL         neg     
 25         RETURN (1)   2
 26         JUMP         L14
 27  L13:   LOAD         -1[LB]
 28         LOADL        1
 29         CALL         add     
 30         RETURN (1)   2
 31  L14:   LOAD         -1[LB]
 32         LOADL        17
 33         CALL         add     
 34         RETURN (1)   1
 35  L15:   RETURN (0)   0
