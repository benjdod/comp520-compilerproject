  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L17
  4         HALT   (1)   
  5         LOAD         -1[LB]
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
 33  L12:   LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39         LOAD         -2[LB]
 40         CALL         arraylen
 41         LOAD         -1[LB]
 42         CALL         arraylen
 43         CALL         ne      
 44         JUMPIF (0)   L14
 45  L13:   LOADL        0
 46         RETURN (1)   1
 47  L14:   LOAD         -1[LB]
 48         CALL         arraylen
 49         LOADL        0
 50  L15:   LOAD         4[LB]
 51         LOAD         3[LB]
 52         CALL         lt      
 53         JUMPIF (0)   L16
 54         LOAD         -2[LB]
 55         LOAD         4[LB]
 56         CALL         fieldref
 57         LOAD         -1[LB]
 58         LOAD         4[LB]
 59         CALL         fieldref
 60         CALL         eq      
 61         JUMPIF (0)   L13
 62         CALL         succ    
 63         JUMP         ***
 64  L16:   LOADL        1
 65         RETURN (1)   1
 66         LOAD         -1[LB]
 67         CALL         arraylen
 68         RETURN (1)   0
 69  L17:   PUSH         1
 70         LOADL        0
 71         STORE        3[LB]
 72         JUMP         L18
 73  L18:   LOAD         3[LB]
 74         JUMPIF (0)   L20
 75         LOADL        7
 76         CALL         newarr  
 77         LOAD         -1[ST]
 78         LOADL        0
 79         LOADL        101
 80         CALL         arrayupd
 81         LOAD         -1[ST]
 82         LOADL        1
 83         LOADL        120
 84         CALL         arrayupd
 85         LOAD         -1[ST]
 86         LOADL        2
 87         LOADL        105
 88         CALL         arrayupd
 89         LOAD         -1[ST]
 90         LOADL        3
 91         LOADL        116
 92         CALL         arrayupd
 93         LOAD         -1[ST]
 94         LOADL        4
 95         LOADL        105
 96         CALL         arrayupd
 97         LOAD         -1[ST]
 98         LOADL        5
 99         LOADL        110
100         CALL         arrayupd
101         LOAD         -1[ST]
102         LOADL        6
103         LOADL        103
104         CALL         arrayupd
105         CALL         L12
106         JUMP         L22
107  L19:   JUMP         L21
108  L20:   LOADL        9
109         CALL         newarr  
110         LOAD         -1[ST]
111         LOADL        0
112         LOADL        115
113         CALL         arrayupd
114         LOAD         -1[ST]
115         LOADL        1
116         LOADL        101
117         CALL         arrayupd
118         LOAD         -1[ST]
119         LOADL        2
120         LOADL        116
121         CALL         arrayupd
122         LOAD         -1[ST]
123         LOADL        3
124         LOADL        116
125         CALL         arrayupd
126         LOAD         -1[ST]
127         LOADL        4
128         LOADL        105
129         CALL         arrayupd
130         LOAD         -1[ST]
131         LOADL        5
132         LOADL        110
133         CALL         arrayupd
134         LOAD         -1[ST]
135         LOADL        6
136         LOADL        103
137         CALL         arrayupd
138         LOAD         -1[ST]
139         LOADL        7
140         LOADL        32
141         CALL         arrayupd
142         LOAD         -1[ST]
143         LOADL        8
144         LOADL        98
145         CALL         arrayupd
146         CALL         L12
147         LOADL        1
148         STORE        3[LB]
149  L21:   JUMP         L18
150  L22:   POP          0
151         RETURN (0)   1
