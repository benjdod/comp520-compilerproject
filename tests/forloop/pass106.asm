  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L16
  5         HALT   (0)   
  6         LOAD         -1[LB]
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
 34         LOAD         -1[LB]
 35         JUMPIF (0)   L10
 36         LOAD         -1[LB]
 37         CALL         putarr  
 38         CALL         puteol  
 39         RETURN (0)   1
 40         LOAD         -2[LB]
 41         CALL         arraylen
 42         LOAD         -1[LB]
 43         CALL         arraylen
 44         CALL         ne      
 45         JUMPIF (0)   L13
 46  L12:   LOADL        0
 47         RETURN (1)   1
 48  L13:   LOAD         -1[LB]
 49         CALL         arraylen
 50         LOADL        0
 51  L14:   LOAD         4[LB]
 52         LOAD         3[LB]
 53         CALL         lt      
 54         JUMPIF (0)   L15
 55         LOAD         -2[LB]
 56         LOAD         4[LB]
 57         CALL         fieldref
 58         LOAD         -1[LB]
 59         LOAD         4[LB]
 60         CALL         fieldref
 61         CALL         eq      
 62         JUMPIF (0)   L12
 63         CALL         succ    
 64         JUMP         ***
 65  L15:   LOADL        1
 66         RETURN (1)   1
 67         LOAD         -1[LB]
 68         CALL         arraylen
 69         RETURN (1)   0
 70  L16:   LOADL        10
 71         CALL         L20
 72         PUSH         1
 73         LOADL        0
 74         STORE        3[LB]
 75         PUSH         1
 76         LOADL        10
 77         CALL         newarr  
 78         STORE        4[LB]
 79         JUMP         L18
 80  L17:   LOAD         3[LB]
 81         LOADL        1
 82         CALL         add     
 83         STORE        3[LB]
 84  L18:   LOAD         3[LB]
 85         LOAD         4[LB]
 86         CALL         arraylen
 87         CALL         lt      
 88         JUMPIF (0)   L19
 89         LOAD         4[LB]
 90         LOAD         3[LB]
 91         LOAD         3[LB]
 92         LOADL        3
 93         CALL         mod     
 94         CALL         arrayupd
 95         PUSH         1
 96         CALL         L21
 97         STORE        5[LB]
 98         LOAD         5[LB]
 99         LOADL        9
100         LOAD         3[LB]
101         CALL         sub     
102         LOADL        14
103         CALL         arrayupd
104         POP          1
105         JUMP         L17
106  L19:   POP          1
107         RETURN (0)   1
108  L20:   LOAD         -1[LB]
109         CALL         newarr  
110         STORE        1[SB]
111         RETURN (0)   1
112  L21:   LOAD         1[SB]
113         RETURN (1)   0
