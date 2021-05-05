  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L12
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L12:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33         LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   PUSH         1
 40         LOADL        0
 41         STORE        3[LB]
 42  L14:   LOAD         3[LB]
 43         LOADL        10
 44         CALL         lt      
 45         JUMPIF (0)   L16
 46         LOAD         3[LB]
 47         LOADL        5
 48         CALL         ge      
 49         JUMPIF (0)   L15
 50         JUMP         L16
 51  L15:   LOAD         3[LB]
 52         LOADL        1
 53         CALL         add     
 54         STORE        3[LB]
 55         JUMP         L14
 56  L16:   PUSH         1
 57         LOADL        0
 58         STORE        4[LB]
 59         PUSH         1
 60         LOADL        0
 61         STORE        5[LB]
 62  L17:   LOAD         4[LB]
 63         LOADL        150
 64         CALL         lt      
 65         JUMPIF (0)   L21
 66         LOAD         4[LB]
 67         LOADL        1
 68         CALL         add     
 69         STORE        4[LB]
 70         LOAD         5[LB]
 71         LOADL        100
 72         CALL         ge      
 73         JUMPIF (0)   L19
 74         JUMP         L17
 75  L18:   JUMP         L20
 76  L19:   LOAD         4[LB]
 77         LOAD         4[LB]
 78         CALL         mult    
 79         STORE        5[LB]
 80  L20:   JUMP         L17
 81  L21:   LOAD         3[LB]
 82         CALL         L11
 83         LOAD         5[LB]
 84         CALL         L11
 85         RETURN (0)   1
