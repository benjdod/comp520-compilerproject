  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L15
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L10
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L10:   LOADL        102
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
 33  L11:   LOAD         -1[LB]
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
 60  L15:   CALL         L16
 61         RETURN (0)   1
 62  L16:   LOADL        24
 63         CALL         newarr  
 64         LOAD         -1[ST]
 65         LOADL        0
 66         LOADL        73
 67         CALL         arrayupd
 68         LOAD         -1[ST]
 69         LOADL        1
 70         LOADL        39
 71         CALL         arrayupd
 72         LOAD         -1[ST]
 73         LOADL        2
 74         LOADL        109
 75         CALL         arrayupd
 76         LOAD         -1[ST]
 77         LOADL        3
 78         LOADL        32
 79         CALL         arrayupd
 80         LOAD         -1[ST]
 81         LOADL        4
 82         LOADL        116
 83         CALL         arrayupd
 84         LOAD         -1[ST]
 85         LOADL        5
 86         LOADL        104
 87         CALL         arrayupd
 88         LOAD         -1[ST]
 89         LOADL        6
 90         LOADL        101
 91         CALL         arrayupd
 92         LOAD         -1[ST]
 93         LOADL        7
 94         LOADL        32
 95         CALL         arrayupd
 96         LOAD         -1[ST]
 97         LOADL        8
 98         LOADL        114
 99         CALL         arrayupd
100         LOAD         -1[ST]
101         LOADL        9
102         LOADL        101
103         CALL         arrayupd
104         LOAD         -1[ST]
105         LOADL        10
106         LOADL        97
107         CALL         arrayupd
108         LOAD         -1[ST]
109         LOADL        11
110         LOADL        108
111         CALL         arrayupd
112         LOAD         -1[ST]
113         LOADL        12
114         LOADL        32
115         CALL         arrayupd
116         LOAD         -1[ST]
117         LOADL        13
118         LOADL        109
119         CALL         arrayupd
120         LOAD         -1[ST]
121         LOADL        14
122         LOADL        97
123         CALL         arrayupd
124         LOAD         -1[ST]
125         LOADL        15
126         LOADL        105
127         CALL         arrayupd
128         LOAD         -1[ST]
129         LOADL        16
130         LOADL        110
131         CALL         arrayupd
132         LOAD         -1[ST]
133         LOADL        17
134         LOADL        32
135         CALL         arrayupd
136         LOAD         -1[ST]
137         LOADL        18
138         LOADL        109
139         CALL         arrayupd
140         LOAD         -1[ST]
141         LOADL        19
142         LOADL        101
143         CALL         arrayupd
144         LOAD         -1[ST]
145         LOADL        20
146         LOADL        116
147         CALL         arrayupd
148         LOAD         -1[ST]
149         LOADL        21
150         LOADL        104
151         CALL         arrayupd
152         LOAD         -1[ST]
153         LOADL        22
154         LOADL        111
155         CALL         arrayupd
156         LOAD         -1[ST]
157         LOADL        23
158         LOADL        100
159         CALL         arrayupd
160         CALL         L11
161         RETURN (0)   0
