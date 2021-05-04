  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L16
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   CALL         puteol  
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
 34         JUMPIF (0)   L14
 35         PUSH         1
 36         LOADL        0
 37         LOAD         -1[LB]
 38         CALL         arraylen
 39         STORE        3[LB]
 40  L13:   LOAD         3[LB]
 41         LOAD         4[LB]
 42         CALL         sub     
 43         JUMPIF (0)   L15
 44         LOAD         -1[LB]
 45         LOAD         4[LB]
 46         CALL         arrayref
 47         CALL         put     
 48         CALL         succ    
 49         JUMP         L13
 50  L14:   LOADL        110
 51         CALL         put     
 52         LOADL        117
 53         CALL         put     
 54         LOADL        108
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58  L15:   CALL         puteol  
 59         RETURN (0)   1
 60  L16:   PUSH         1
 61         LOADL        0
 62         STORE        3[LB]
 63         PUSH         1
 64         LOADL        20
 65         STORE        4[LB]
 66         JUMP         L18
 67  L17:   LOAD         3[LB]
 68         LOADL        1
 69         CALL         add     
 70         STORE        3[LB]
 71         LOAD         4[LB]
 72         LOADL        1
 73         CALL         sub     
 74         STORE        4[LB]
 75  L18:   LOAD         3[LB]
 76         LOAD         4[LB]
 77         CALL         le      
 78         JUMPIF (0)   L19
 79         LOAD         3[LB]
 80         CALL         L10
 81         LOAD         4[LB]
 82         CALL         L10
 83         CALL         L11
 84         JUMP         L17
 85  L19:   POP          2
 86         RETURN (0)   1
