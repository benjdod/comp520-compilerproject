  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L12
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L11
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         CALL         puteol  
 21         RETURN (0)   1
 22  L11:   LOADL        102
 23         CALL         put     
 24         LOADL        97
 25         CALL         put     
 26         LOADL        108
 27         CALL         put     
 28         LOADL        115
 29         CALL         put     
 30         LOADL        101
 31         CALL         put     
 32         CALL         puteol  
 33         RETURN (0)   1
 34  L12:   PUSH         1
 35         LOADL        0
 36         STORE        3[LB]
 37  L13:   LOAD         3[LB]
 38         LOADL        10
 39         CALL         lt      
 40         JUMPIF (0)   L15
 41         LOAD         3[LB]
 42         LOADL        5
 43         CALL         ge      
 44         JUMPIF (0)   L14
 45         JUMP         L15
 46  L14:   LOAD         3[LB]
 47         LOADL        1
 48         CALL         add     
 49         STORE        3[LB]
 50         JUMP         L13
 51  L15:   PUSH         1
 52         LOADL        0
 53         STORE        4[LB]
 54         PUSH         1
 55         LOADL        0
 56         STORE        5[LB]
 57  L16:   LOAD         4[LB]
 58         LOADL        150
 59         CALL         lt      
 60         JUMPIF (0)   L20
 61         LOAD         4[LB]
 62         LOADL        1
 63         CALL         add     
 64         STORE        4[LB]
 65         LOAD         5[LB]
 66         LOADL        100
 67         CALL         ge      
 68         JUMPIF (0)   L18
 69         JUMP         L16
 70  L17:   JUMP         L19
 71  L18:   LOAD         4[LB]
 72         LOAD         4[LB]
 73         CALL         mult    
 74         STORE        5[LB]
 75  L19:   JUMP         L16
 76  L20:   LOAD         3[LB]
 77         CALL         L10
 78         LOAD         5[LB]
 79         CALL         L10
 80         RETURN (0)   1
