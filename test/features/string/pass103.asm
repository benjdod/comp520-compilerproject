  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L15
  5         HALT   (0)   
  6         LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L10
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         RETURN (0)   1
 22  L10:   LOADL        102
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
 34  L11:   LOAD         -1[LB]
 35         JUMPIF (0)   L13
 36         PUSH         1
 37         LOADL        0
 38         LOAD         -1[LB]
 39         CALL         arraylen
 40         STORE        3[LB]
 41  L12:   LOAD         3[LB]
 42         LOAD         4[LB]
 43         CALL         sub     
 44         JUMPIF (0)   L14
 45         LOAD         -1[LB]
 46         LOAD         4[LB]
 47         CALL         arrayref
 48         CALL         put     
 49         CALL         succ    
 50         JUMP         L12
 51  L13:   LOADL        110
 52         CALL         put     
 53         LOADL        117
 54         CALL         put     
 55         LOADL        108
 56         CALL         put     
 57         LOADL        108
 58         CALL         put     
 59  L14:   CALL         puteol  
 60         RETURN (0)   1
 61  L15:   LOADL        0
 62         STORE        1[SB]
 63         LOAD         1[SB]
 64         CALL         L11
 65         LOADL        16
 66         CALL         newarr  
 67         LOAD         -1[ST]
 68         LOADL        0
 69         LOADL        110
 70         CALL         arrayupd
 71         LOAD         -1[ST]
 72         LOADL        1
 73         LOADL        111
 74         CALL         arrayupd
 75         LOAD         -1[ST]
 76         LOADL        2
 77         LOADL        116
 78         CALL         arrayupd
 79         LOAD         -1[ST]
 80         LOADL        3
 81         LOADL        32
 82         CALL         arrayupd
 83         LOAD         -1[ST]
 84         LOADL        4
 85         LOADL        110
 86         CALL         arrayupd
 87         LOAD         -1[ST]
 88         LOADL        5
 89         LOADL        117
 90         CALL         arrayupd
 91         LOAD         -1[ST]
 92         LOADL        6
 93         LOADL        108
 94         CALL         arrayupd
 95         LOAD         -1[ST]
 96         LOADL        7
 97         LOADL        108
 98         CALL         arrayupd
 99         LOAD         -1[ST]
100         LOADL        8
101         LOADL        32
102         CALL         arrayupd
103         LOAD         -1[ST]
104         LOADL        9
105         LOADL        97
106         CALL         arrayupd
107         LOAD         -1[ST]
108         LOADL        10
109         LOADL        110
110         CALL         arrayupd
111         LOAD         -1[ST]
112         LOADL        11
113         LOADL        121
114         CALL         arrayupd
115         LOAD         -1[ST]
116         LOADL        12
117         LOADL        109
118         CALL         arrayupd
119         LOAD         -1[ST]
120         LOADL        13
121         LOADL        111
122         CALL         arrayupd
123         LOAD         -1[ST]
124         LOADL        14
125         LOADL        114
126         CALL         arrayupd
127         LOAD         -1[ST]
128         LOADL        15
129         LOADL        101
130         CALL         arrayupd
131         STORE        1[SB]
132         LOAD         1[SB]
133         CALL         L11
134         LOADL        0
135         CALL         L11
136         RETURN (0)   1
