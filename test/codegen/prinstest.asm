  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L11
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   PUSH         1
  9         LOADL        1
 10         STORE        3[LB]
 11         LOAD         3[LB]
 12         CALL         L10
 13         LOADL        2
 14         LOAD         3[LB]
 15         CALL         mult    
 16         LOAD         3[LB]
 17         CALL         add     
 18         LOADL        1
 19         CALL         sub     
 20         STORE        3[LB]
 21         LOAD         3[LB]
 22         CALL         L10
 23         LOADL        3
 24         CALL         L10
 25         LOAD         3[LB]
 26         LOADL        1
 27         CALL         neg     
 28         CALL         ne      
 29         JUMPIF (0)   L12
 30         LOADL        4
 31         CALL         L10
 32         JUMP         L13
 33  L12:   LOADL        1
 34         CALL         neg     
 35         CALL         L10
 36  L13:   PUSH         1
 37         LOADL        0
 38         STORE        4[LB]
 39  L14:   LOAD         4[LB]
 40         LOADL        5
 41         CALL         lt      
 42         JUMPIF (0)   L15
 43         LOAD         4[LB]
 44         LOADL        1
 45         CALL         add     
 46         STORE        4[LB]
 47         LOAD         4[LB]
 48         STORE        3[LB]
 49         JUMP         L14
 50  L15:   LOAD         3[LB]
 51         CALL         L10
 52         PUSH         1
 53         LOADL        -1
 54         LOADL        2
 55         CALL         newobj  
 56         STORE        5[LB]
 57         LOAD         5[LB]
 58         LOADL        0
 59         CALL         ne      
 60         JUMPIF (0)   L16
 61         LOADL        6
 62         CALL         L10
 63  L16:   LOADL        7
 64         LOAD         5[LB]
 65         LOADL        0
 66         CALL         fieldref
 67         CALL         add     
 68         STORE        3[LB]
 69         LOAD         3[LB]
 70         CALL         L10
 71         LOAD         5[LB]
 72         LOADL        1
 73         LOADL        -1
 74         LOADL        2
 75         CALL         newobj  
 76         CALL         fieldupd
 77         LOAD         5[LB]
 78         LOADL        1
 79         CALL         fieldref
 80         LOADL        0
 81         LOADL        8
 82         CALL         fieldupd
 83         LOAD         5[LB]
 84         LOADL        1
 85         CALL         fieldref
 86         LOADL        0
 87         CALL         fieldref
 88         CALL         L10
 89         PUSH         1
 90         LOADL        4
 91         CALL         newarr  
 92         STORE        6[LB]
 93         LOAD         6[LB]
 94         CALL         arraylen
 95         STORE        3[LB]
 96         LOADL        2
 97         LOAD         3[LB]
 98         CALL         mult    
 99         LOADL        1
100         CALL         add     
101         CALL         L10
102         LOAD         6[LB]
103         LOADL        0
104         LOADL        0
105         CALL         arrayupd
106         LOADL        1
107         STORE        4[LB]
108  L17:   LOAD         4[LB]
109         LOAD         6[LB]
110         CALL         arraylen
111         CALL         lt      
112         JUMPIF (0)   L18
113         LOAD         6[LB]
114         LOAD         4[LB]
115         LOAD         6[LB]
116         LOAD         4[LB]
117         LOADL        1
118         CALL         sub     
119         CALL         arrayref
120         LOAD         4[LB]
121         CALL         add     
122         CALL         arrayupd
123         LOAD         4[LB]
124         LOADL        1
125         CALL         add     
126         STORE        4[LB]
127         JUMP         L17
128  L18:   LOAD         6[LB]
129         LOADL        3
130         CALL         arrayref
131         LOADL        4
132         CALL         add     
133         STORE        3[LB]
134         LOAD         3[LB]
135         CALL         L10
136         LOAD         5[LB]
137         CALLI        L19
138         LOADL        999
139         CALL         L10
140         RETURN (0)   1
141  L19:   PUSH         1
142         LOADL        11
143         STORE        3[LB]
144         LOAD         3[LB]
145         CALL         L10
146         LOAD         1[OB]
147         LOADL        1
148         LOADA        0[OB]
149         CALL         fieldupd
150         LOADL        12
151         STORE        0[OB]
152         LOAD         1[OB]
153         LOADL        1
154         CALL         fieldref
155         LOADL        0
156         CALL         fieldref
157         STORE        3[LB]
158         LOAD         3[LB]
159         CALL         L10
160         LOADL        4
161         STORE        0[OB]
162         LOADL        2
163         LOADL        3
164         LOADL        4
165         LOADA        0[OB]
166         CALLI        L20
167         CALL         add     
168         STORE        3[LB]
169         LOAD         3[LB]
170         CALL         L10
171         LOADL        8
172         LOADL        3
173         LOAD         1[OB]
174         CALLI        L22
175         CALL         add     
176         CALL         L10
177         LOADA        0[OB]
178         LOADL        0
179         LOADL        4
180         CALL         fieldupd
181         LOAD         1[OB]
182         LOADL        0
183         LOADL        5
184         CALL         fieldupd
185         LOADL        2
186         LOADA        0[OB]
187         LOADA        0[OB]
188         LOADL        1
189         CALL         fieldref
190         LOADA        0[OB]
191         CALLI        L21
192         CALL         add     
193         CALL         L10
194         RETURN (0)   0
195  L20:   LOAD         0[OB]
196         LOAD         -2[LB]
197         CALL         add     
198         LOAD         -1[LB]
199         CALL         add     
200         CALL         L10
201         LOAD         0[OB]
202         LOAD         -2[LB]
203         CALL         add     
204         LOAD         -1[LB]
205         CALL         add     
206         RETURN (1)   2
207  L21:   LOAD         -2[LB]
208         LOADL        0
209         CALL         fieldref
210         LOAD         -1[LB]
211         LOADL        0
212         CALL         fieldref
213         CALL         add     
214         LOADA        0[OB]
215         LOADL        0
216         CALL         fieldref
217         CALL         add     
218         RETURN (1)   2
219  L22:   PUSH         1
220         LOADL        1
221         STORE        3[LB]
222         LOAD         -1[LB]
223         LOADL        1
224         CALL         gt      
225         JUMPIF (0)   L23
226         LOAD         -1[LB]
227         LOAD         -1[LB]
228         LOADL        1
229         CALL         sub     
230         LOADA        0[OB]
231         CALLI        L22
232         CALL         mult    
233         STORE        3[LB]
234  L23:   LOAD         3[LB]
235         RETURN (1)   1
