  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L14
  3         HALT   (0)   
  4         PUSH         1
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         PUSH         1
  9         LOAD         -1[LB]
 10         STORE        3[LB]
 11         RETURN (0)   0
 12         LOAD         0[OB]
 13         LOADL        1
 14         CALL         add     
 15         STORE        0[OB]
 16         RETURN (0)   0
 17  L11:   LOAD         -1[LB]
 18         STORE        0[OB]
 19         RETURN (0)   0
 20  L12:   LOAD         -1[LB]
 21         STORE        1[OB]
 22         RETURN (0)   0
 23  L13:   LOAD         0[OB]
 24         LOAD         1[OB]
 25         CALL         add     
 26         RETURN (1)   0
 27  L14:   PUSH         1
 28         LOADL        -1
 29         LOADL        2
 30         CALL         newobj  
 31         STORE        3[LB]
 32         LOADL        5
 33         LOAD         3[LB]
 34         CALLI        L11
 35         LOADL        3
 36         LOAD         3[LB]
 37         CALLI        L12
 38         LOAD         3[LB]
 39         CALLI        L13
 40         LOAD         5[CB]
 41         CALLI        L10
 42         RETURN (0)   0
