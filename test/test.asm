  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L14
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   LOADL        1
 10         RETURN (1)   0
 11  L12:   LOADL        1
 12         LOAD         -1[LB]
 13         CALL         add     
 14         STORE        1[SB]
 15         LOADL        1
 16         LOAD         -1[LB]
 17         CALL         add     
 18         RETURN (1)   1
 19  L13:   LOAD         -2[LB]
 20         LOAD         -1[LB]
 21         CALL         add     
 22         RETURN (1)   2
 23  L14:   LOADL        4
 24         CALL         L12
 25         POP          1
 26         CALL         L11
 27         POP          1
 28         LOADL        9
 29         CALL         L12
 30         CALL         L10
 31         LOADL        80
 32         LOADL        90
 33         CALL         L13
 34         CALL         L10
 35         RETURN (0)   1
