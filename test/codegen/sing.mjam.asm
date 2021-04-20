  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
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
 17  L11:   PUSH         1
 18         LOADL        10
 19         STORE        3[LB]
 20         PUSH         1
 21         LOAD         3[LB]
 22         CALL         newarr  
 23         STORE        4[LB]
 24         LOAD         4[LB]
 25         LOADL        0
 26         LOADL        60
 27         CALL         arrayupd
 28         LOAD         4[LB]
 29         LOADL        5
 30         LOADL        6
 31         CALL         arrayupd
 32         PUSH         1
 33         LOADL        0
 34         STORE        5[LB]
 35  L12:   LOAD         5[LB]
 36         LOAD         3[LB]
 37         CALL         lt      
 38         JUMPIF (0)   L13
 39         LOAD         4[LB]
 40         LOAD         5[LB]
 41         CALL         arrayref
 42         LOAD         5[CB]
 43         CALLI        L10
 44         LOAD         5[LB]
 45         LOADL        1
 46         CALL         add     
 47         STORE        5[LB]
 48         JUMP         L12
 49  L13:   RETURN (0)   0
