  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L14
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L11
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         CALL         puteol  
 22         RETURN (0)   1
 23  L11:   LOADL        102
 24         CALL         put     
 25         LOADL        97
 26         CALL         put     
 27         LOADL        108
 28         CALL         put     
 29         LOADL        115
 30         CALL         put     
 31         LOADL        101
 32         CALL         put     
 33         CALL         puteol  
 34         RETURN (0)   1
 35  L12:   LOADL        1
 36         RETURN (1)   0
 37  L13:   LOADL        1
 38         LOAD         -1[LB]
 39         CALL         add     
 40         STORE        1[SB]
 41         LOADL        1
 42         LOAD         -1[LB]
 43         CALL         add     
 44         RETURN (1)   1
 45         LOAD         -2[LB]
 46         LOAD         -1[LB]
 47         CALL         add     
 48         RETURN (1)   2
 49  L14:   LOADL        4
 50         CALL         L13
 51         POP          1
 52         CALL         L12
 53         POP          1
 54         PUSH         1
 55         LOADL        4
 56         STORE        3[LB]
 57         LOAD         3[LB]
 58         LOADL        1
 59         LOADL        2
 60         CALL         add     
 61         CALL         mult    
 62         STORE        3[LB]
 63         LOAD         3[LB]
 64         CALL         L10
 65         RETURN (0)   1
