  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L11
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9  L11:   CALL         L12
 10         CALL         L19
 11         LOADL        6666666
 12         CALL         L10
 13         CALL         L13
 14         CALL         L19
 15         RETURN (0)   1
 16  L12:   LOADL        15
 17         CALL         newarr  
 18         STORE        1[SB]
 19         LOAD         1[SB]
 20         LOADL        0
 21         LOADL        66
 22         CALL         neg     
 23         CALL         arrayupd
 24         LOAD         1[SB]
 25         LOADL        1
 26         LOADL        10
 27         CALL         arrayupd
 28         LOAD         1[SB]
 29         LOADL        2
 30         LOADL        20
 31         CALL         arrayupd
 32         LOAD         1[SB]
 33         LOADL        3
 34         LOADL        30
 35         CALL         arrayupd
 36         LOAD         1[SB]
 37         LOADL        4
 38         LOADL        12
 39         CALL         arrayupd
 40         LOAD         1[SB]
 41         LOADL        5
 42         LOADL        17
 43         CALL         arrayupd
 44         LOAD         1[SB]
 45         LOADL        6
 46         LOADL        12
 47         CALL         neg     
 48         CALL         arrayupd
 49         LOAD         1[SB]
 50         LOADL        8
 51         LOADL        0
 52         CALL         arrayupd
 53         LOAD         1[SB]
 54         LOADL        9
 55         LOADL        128
 56         CALL         arrayupd
 57         LOAD         1[SB]
 58         LOADL        10
 59         LOADL        4
 60         CALL         arrayupd
 61         LOAD         1[SB]
 62         LOADL        11
 63         LOADL        8
 64         CALL         arrayupd
 65         LOAD         1[SB]
 66         LOADL        12
 67         LOADL        36
 68         CALL         neg     
 69         CALL         arrayupd
 70         LOAD         1[SB]
 71         LOADL        13
 72         LOADL        67
 73         CALL         neg     
 74         CALL         arrayupd
 75         LOAD         1[SB]
 76         LOADL        14
 77         LOADL        10000
 78         CALL         arrayupd
 79         RETURN (0)   0
 80  L13:   PUSH         1
 81         LOADL        0
 82         STORE        3[LB]
 83  L14:   LOAD         3[LB]
 84         LOAD         1[SB]
 85         CALL         arraylen
 86         LOADL        1
 87         CALL         sub     
 88         CALL         lt      
 89         JUMPIF (0)   L18
 90         PUSH         1
 91         LOADL        0
 92         STORE        4[LB]
 93  L15:   LOAD         4[LB]
 94         LOAD         1[SB]
 95         CALL         arraylen
 96         LOADL        1
 97         CALL         sub     
 98         CALL         lt      
 99         JUMPIF (0)   L17
100         PUSH         1
101         LOAD         4[LB]
102         LOADL        1
103         CALL         add     
104         STORE        5[LB]
105         LOAD         1[SB]
106         LOAD         4[LB]
107         CALL         arrayref
108         LOAD         1[SB]
109         LOAD         5[LB]
110         CALL         arrayref
111         CALL         ge      
112         JUMPIF (0)   L16
113         PUSH         1
114         LOAD         1[SB]
115         LOAD         5[LB]
116         CALL         arrayref
117         STORE        6[LB]
118         LOAD         1[SB]
119         LOAD         5[LB]
120         LOAD         1[SB]
121         LOAD         4[LB]
122         CALL         arrayref
123         CALL         arrayupd
124         LOAD         1[SB]
125         LOAD         4[LB]
126         LOAD         6[LB]
127         CALL         arrayupd
128         POP          1
129  L16:   LOAD         4[LB]
130         LOADL        1
131         CALL         add     
132         STORE        4[LB]
133         POP          1
134         JUMP         L15
135  L17:   LOAD         3[LB]
136         LOADL        1
137         CALL         add     
138         STORE        3[LB]
139         POP          1
140         JUMP         L14
141  L18:   RETURN (0)   0
142  L19:   PUSH         1
143         LOADL        0
144         STORE        3[LB]
145  L20:   LOAD         3[LB]
146         LOAD         1[SB]
147         CALL         arraylen
148         CALL         lt      
149         JUMPIF (0)   L21
150         LOAD         1[SB]
151         LOAD         3[LB]
152         CALL         arrayref
153         CALL         L10
154         LOAD         3[LB]
155         LOADL        1
156         CALL         add     
157         STORE        3[LB]
158         JUMP         L20
159  L21:   RETURN (0)   0
