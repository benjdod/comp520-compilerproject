  0         PUSH         1
  1         PUSH         1
  2         PUSH         1
  3         LOADL        0
  4         CALL         newarr  
  5         CALL         L20
  6         HALT   (0)   
  7         LOAD         -1[LB]
  8         CALL         putintnl
  9         RETURN (0)   1
 10         CALL         puteol  
 11         RETURN (0)   0
 12  L10:   LOAD         -1[LB]
 13         JUMPIF (0)   L11
 14         LOADL        116
 15         CALL         put     
 16         LOADL        114
 17         CALL         put     
 18         LOADL        117
 19         CALL         put     
 20         LOADL        101
 21         CALL         put     
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
 35         LOAD         -1[LB]
 36         JUMPIF (0)   L13
 37         PUSH         1
 38         LOADL        0
 39         LOAD         -1[LB]
 40         CALL         arraylen
 41         STORE        3[LB]
 42  L12:   LOAD         3[LB]
 43         LOAD         4[LB]
 44         CALL         sub     
 45         JUMPIF (0)   L14
 46         LOAD         -1[LB]
 47         LOAD         4[LB]
 48         CALL         arrayref
 49         CALL         put     
 50         CALL         succ    
 51         JUMP         L12
 52  L13:   LOADL        110
 53         CALL         put     
 54         LOADL        117
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58         LOADL        108
 59         CALL         put     
 60  L14:   CALL         puteol  
 61         RETURN (0)   1
 62  L15:   LOAD         -2[LB]
 63         CALL         arraylen
 64         LOAD         -1[LB]
 65         CALL         arraylen
 66         CALL         ne      
 67         JUMPIF (0)   L17
 68  L16:   LOADL        0
 69         RETURN (1)   1
 70  L17:   LOAD         -1[LB]
 71         CALL         arraylen
 72         LOADL        0
 73  L18:   LOAD         4[LB]
 74         LOAD         3[LB]
 75         CALL         lt      
 76         JUMPIF (0)   L19
 77         LOAD         -2[LB]
 78         LOAD         4[LB]
 79         CALL         fieldref
 80         LOAD         -1[LB]
 81         LOAD         4[LB]
 82         CALL         fieldref
 83         CALL         eq      
 84         JUMPIF (0)   L16
 85         CALL         succ    
 86         JUMP         ***
 87  L19:   LOADL        1
 88         RETURN (1)   1
 89         LOAD         -1[LB]
 90         CALL         arraylen
 91         RETURN (1)   0
 92  L20:   PUSH         1
 93         LOADL        3
 94         CALL         newarr  
 95         LOAD         -1[ST]
 96         LOADL        0
 97         LOADL        97
 98         CALL         arrayupd
 99         LOAD         -1[ST]
100         LOADL        1
101         LOADL        98
102         CALL         arrayupd
103         LOAD         -1[ST]
104         LOADL        2
105         LOADL        99
106         CALL         arrayupd
107         STORE        3[LB]
108         LOADL        3
109         CALL         newarr  
110         LOAD         -1[ST]
111         LOADL        0
112         LOADL        97
113         CALL         arrayupd
114         LOAD         -1[ST]
115         LOADL        1
116         LOADL        98
117         CALL         arrayupd
118         LOAD         -1[ST]
119         LOADL        2
120         LOADL        99
121         CALL         arrayupd
122         LOAD         3[LB]
123         CALLI        L15
124         CALL         L10
125         RETURN (0)   1
