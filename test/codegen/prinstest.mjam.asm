  0         LOADL        0
  1         CALL         newarr  
  2         CALL         L11
  3         HALT   (0)   
  4         PUSH         1
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   PUSH         1
  9         LOADL        1
 10         STORE        3[LB]
 11         LOADL        2
 12         LOAD         3[LB]
 13         CALL         mult    
 14         LOAD         3[LB]
 15         CALL         add     
 16         LOADL        1
 17         CALL         sub     
 18         STORE        3[LB]
 19         LOAD         3[LB]
 20         CALL         L10
 21         LOADL        3
 22         CALL         L10
 23         LOAD         3[LB]
 24         LOADL        1
 25         CALL         neg     
 26         CALL         ne      
 27         JUMPIF (0)   L12
 28         LOADL        4
 29         CALL         L10
 30         JUMP         L13
 31  L12:   LOADL        1
 32         CALL         neg     
 33         CALL         L10
 34  L13:   PUSH         1
 35         LOADL        0
 36         STORE        4[LB]
 37  L14:   LOAD         4[LB]
 38         LOADL        5
 39         CALL         lt      
 40         JUMPIF (0)   L15
 41         LOAD         4[LB]
 42         LOADL        1
 43         CALL         add     
 44         STORE        4[LB]
 45         LOAD         4[LB]
 46         STORE        3[LB]
 47         JUMP         L14
 48  L15:   LOAD         3[LB]
 49         CALL         L10
 50         PUSH         1
 51         LOADL        -1
 52         LOADL        2
 53         CALL         newobj  
 54         STORE        5[LB]
 55         LOAD         5[LB]
 56         LOADL        0
 57         CALL         ne      
 58         JUMPIF (0)   L16
 59         LOADL        6
 60         CALL         L10
 61  L16:   LOADL        7
 62         LOAD         5[LB]
 63         LOADL        0
 64         CALL         fieldref
 65         CALL         add     
 66         STORE        3[LB]
 67         LOAD         3[LB]
 68         CALL         L10
 69         LOAD         5[LB]
 70         LOADL        1
 71         LOADL        -1
 72         LOADL        2
 73         CALL         newobj  
 74         CALL         fieldupd
 75         LOAD         5[LB]
 76         LOADL        1
 77         CALL         fieldref
 78         LOADL        0
 79         LOADL        8
 80         CALL         fieldupd
 81         LOAD         5[LB]
 82         LOADL        1
 83         CALL         fieldref
 84         LOADL        0
 85         CALL         fieldref
 86         CALL         L10
 87         PUSH         1
 88         LOADL        4
 89         CALL         newarr  
 90         STORE        6[LB]
 91         LOAD         6[LB]
 92         CALL         arraylen
 93         STORE        3[LB]
 94         LOADL        2
 95         LOAD         3[LB]
 96         CALL         mult    
 97         LOADL        1
 98         CALL         add     
 99         CALL         L10
100         LOAD         6[LB]
101         LOADL        0
102         LOADL        0
103         CALL         arrayupd
104         LOADL        1
105         STORE        4[LB]
106  L17:   LOAD         4[LB]
107         LOAD         6[LB]
108         CALL         arraylen
109         CALL         lt      
110         JUMPIF (0)   L18
111         LOAD         6[LB]
112         LOAD         4[LB]
113         LOAD         6[LB]
114         LOAD         4[LB]
115         LOADL        1
116         CALL         sub     
117         CALL         arrayref
118         LOAD         4[LB]
119         CALL         add     
120         CALL         arrayupd
121         LOAD         4[LB]
122         LOADL        1
123         CALL         add     
124         STORE        4[LB]
125         JUMP         L17
126  L18:   LOAD         6[LB]
127         LOADL        3
128         CALL         arrayref
129         LOADL        4
130         CALL         add     
131         STORE        3[LB]
132         LOAD         3[LB]
133         CALL         L10
134         LOAD         5[LB]
135         CALLI        L19
136         LOADL        999
137         CALL         L10
138         RETURN (0)   1
139  L19:   PUSH         1
140         LOADL        11
141         STORE        3[LB]
142         LOAD         3[LB]
143         CALL         L10
144         LOAD         1[OB]
145         LOADL        1
146         LOADA        0[OB]
147         CALL         fieldupd
148         LOADL        12
149         STORE        0[OB]
150         LOAD         1[OB]
151         LOADL        1
152         CALL         fieldref
153         LOADL        0
154         CALL         fieldref
155         STORE        3[LB]
156         LOAD         3[LB]
157         CALL         L10
158         LOADL        4
159         STORE        0[OB]
160         LOADL        2
161         LOADL        3
162         LOADL        4
163         LOADA        0[OB]
164         CALLI        L20
165         CALL         add     
166         STORE        3[LB]
167         LOAD         3[LB]
168         CALL         L10
169         LOADL        8
170         LOADL        3
171         LOAD         1[OB]
172         CALLI        L22
173         CALL         add     
174         CALL         L10
175         LOADA        0[OB]
176         LOADL        0
177         LOADL        4
178         CALL         fieldupd
179         LOAD         1[OB]
180         LOADL        0
181         LOADL        5
182         CALL         fieldupd
183         LOADL        2
184         LOADA        0[OB]
185         LOADA        0[OB]
186         LOADL        1
187         CALL         fieldref
188         LOADA        0[OB]
189         CALLI        L21
190         CALL         add     
191         CALL         L10
192         RETURN (0)   0
193  L20:   LOAD         0[OB]
194         LOAD         -2[LB]
195         CALL         add     
196         LOAD         -1[LB]
197         CALL         add     
198         CALL         L10
199         LOAD         0[OB]
200         LOAD         -2[LB]
201         CALL         add     
202         LOAD         -1[LB]
203         CALL         add     
204         RETURN (1)   2
205  L21:   LOAD         -2[LB]
206         LOADL        0
207         CALL         fieldref
208         LOAD         -1[LB]
209         LOADL        0
210         CALL         fieldref
211         CALL         add     
212         LOADA        0[OB]
213         LOADL        0
214         CALL         fieldref
215         CALL         add     
216         RETURN (1)   2
217  L22:   PUSH         1
218         LOADL        1
219         STORE        3[LB]
220         LOAD         -1[LB]
221         LOADL        1
222         CALL         gt      
223         JUMPIF (0)   L23
224         LOAD         -1[LB]
225         LOAD         -1[LB]
226         LOADL        1
227         CALL         sub     
228         LOADA        0[OB]
229         CALLI        L22
230         CALL         mult    
231         STORE        3[LB]
232  L23:   LOAD         3[LB]
233         RETURN (1)   1
