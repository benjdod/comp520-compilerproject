  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L15
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
 20         RETURN (0)   1
 21  L11:   LOADL        102
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
 34         JUMPIF (0)   L13
 35         PUSH         1
 36         LOADL        0
 37         LOAD         -1[LB]
 38         CALL         arraylen
 39         STORE        3[LB]
 40  L12:   LOAD         3[LB]
 41         LOAD         4[LB]
 42         CALL         sub     
 43         JUMPIF (0)   L14
 44         LOAD         -1[LB]
 45         LOAD         4[LB]
 46         CALL         arrayref
 47         CALL         put     
 48         CALL         succ    
 49         JUMP         L12
 50  L13:   LOADL        110
 51         CALL         put     
 52         LOADL        117
 53         CALL         put     
 54         LOADL        108
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58  L14:   CALL         puteol  
 59         RETURN (0)   1
 60  L15:   PUSH         1
 61         LOADL        0
 62         STORE        3[LB]
 63         PUSH         1
 64         LOADL        0
 65         STORE        4[LB]
 66         JUMP         L17
 67  L16:   LOAD         4[LB]
 68         LOADL        1
 69         CALL         sub     
 70         STORE        4[LB]
 71  L17:   PUSH         1
 72         LOAD         4[LB]
 73         STORE        5[LB]
 74         JUMP         L19
 75  L18:   LOAD         5[LB]
 76         LOADL        1
 77         CALL         add     
 78         STORE        5[LB]
 79  L19:   LOAD         5[LB]
 80         LOADL        0
 81         CALL         lt      
 82         JUMPIF (0)   L21
 83         LOAD         5[LB]
 84         LOADL        4
 85         CALL         neg     
 86         CALL         gt      
 87         JUMPIF (0)   L20
 88         JUMP         L18
 89  L20:   LOAD         5[LB]
 90         CALL         L10
 91         JUMP         L18
 92  L21:   POP          1
 93         LOAD         4[LB]
 94         LOADL        5
 95         CALL         neg     
 96         CALL         lt      
 97         JUMPIF (0)   L22
 98         JUMP         L23
 99  L22:   JUMP         L16
100  L23:   POP          1
101         RETURN (0)   1