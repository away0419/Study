최종 작성일 : 2023.09.18.</br>

## React

<details>
    <summary>props</summary>

- component 안에 component를 넣을 경우 다음과 같이 만들어야 한다.
```jsx
function Wrapper({ children}){
    return(
        <div style= {style}>
            {children}
        </div>
    )
}
```

```jsx
function Hello({color, name}){
    return(
        <div>
            안녕하세요
        </div>
    )
}

Hello.defaultProps = {
    name: '기본'
}

```

```jsx
function App(){
    return(
        <Wrapper>
            <Hello name="react" color="red"></Hello>
            <Hello color="pink"></Hello>
        </Wrapper>
    )
}

```

</details>

<details>
    <summary>input 상태 관리</summary>

```jsx
function InputSample(){
    const [text, setText] = useState('');

    const onChange = (e) =>{
        setText(e.target.value);
    };

    
    return(
        <div>
            <input onChange={onChange} value={text}>
        </div>
    )

}
```

</details>

<details>
    <summary>다중 input 상태 관리</summary>

```jsx
function InputSample(){
    const [inputs, setInputs] = useState({
        name: '',
        nickname: ''
    });

    const {name, nickname} = inputs; 

    const onChange = (e)=>{
        const {value, name} = e.target;
        setInputs({
            ...inputs, 
            [name]: value; 
        })
    }

    return(
        <div>
            <input name="name" onChange={onChange} value={name}>
            <input name="nickname" onChange={onChange} value={nickname}>
        </div>
    )
}
```

</details>

<details>
    <summary>DOM 선택</summary>

```jsx
function InputSample(){
    const onReset = () =>{
     nameInput.current.focus();   
    }
    const nameInput = uesRef();

    return(
        <div>
            <input ref={nameInput} />
        </div>
    );
}
```


</details>

<details>
    <summary>배열 랜더링</summary>

- 배열 사용 시 무조건 key로 넣을 겹치지 않는 필드를 넣기.
- key가 없다면 효율적이지 못함.
- react는 배열의 불변성을 보장해야 함. 따라서 push, remove, sort, splice는 사용할 수 없음. 다만, concat 처럼 새로운 배열을 반환하는 메소드는 사용 가능.

</details>


<details>
    <summary>useRef 컴포넌트 변수 만들기</summary>

- useRef로 관리하는 변수는 값이 바뀌어도 리렌더링 되지 않고 바로 조회 가능

```jsx
function App() {
    const nextId = useRdf(4); // 기본 값이 4
}

```

</details>

<details>
    <summary>베열에 항목 추가하기</summary>

- 불변성에 유의하여 작성.

```jsx
function App() {
    const [inputs, setInputs] = useState({
        username: '',
        email: ''
    });

    const nextId = useRef(1);
    const [users, serUsers] = useState([
        {
            id : 1,
            username: 'velopert',
            email: 'public.test@gmail.com'
        }
    ]);

    
    const onCreate = () =>{
        nextId.current+=1;
        const user = {
            id : nextId.current,
            username,
            email
        };
        setUsers(users.concat(user));
    }
}

```
</details>


<details>
    <summary>배열 항목 제거</summary>

- filter가 가장 쉬움

```jsx
function App() {
    const [users, serUsers] = useState([
        {
            id : 1,
            username: 'velopert',
            email: 'public.test@gmail.com'
        }
    ]);
    
    const onRemove = id =>{
        setUsers(users.filter(user=>user.id !== id));
    };

}

```

</details>

<details>
    <summary>배열 항목 수정</summary>

```jsx
function App() {
    const [users, serUsers] = useState([
        {
            id : 1,
            username: 'velopert',
            email: 'public.test@gmail.com'
        }
    ]);

    const onUpdate = id =>{
        setUsers(
            users.map(user=>user.id !== id ? user : {...user, username : "updatename"});
        );
    };
}


```

</details>

<details>
    <summary>useEffect를 이용한 마운트/언마운트/업데이트</summary>

- 마운트(컴포넌트가 처음 나타날 경우), 언마운트(컴포넌트가 사라질 경우), 업데이트(특정 props 바뀔 경우)
- useEffect의 첫 번째 매개변수에는 실행할 로직이 들어감. return에 들어가는 로직은 마운트 후 실행 되며 return 위의 로직들은 마운트 전에 실행 된다.
- 두 번째 매개변수에 값이 없다면 마운트, 언마운트에 실행하겠다는 의미이며 생략 가능, 매개변수가 있을 경우 해당 매개변수의 컴포넌트가 마운트, 언마운트, 변경 될 때 실행된다.
- 참고로 리액트 컴포넌트는 기본적으로 부모가 리렌더링 되면 자식 컴포넌트 또한 리렌더링 됨.

```jsx
function User({user, onRemove, onToggle }){
    useEffect(() = > {
        console.log('컴포넌트가 화면에 나타남');
        return () => {
            console.log('컴포넌트가 화면에서 사라짐');
        }
    },[])
}
```

</details>

<details>
    <summary>userMemo로 연산 값 재사용하기</summary>

- countActiveUsers 함수를 생성할 경우 리렌더링 될 때마다 실행됨.
- userMemo는 이전에 계산한 값을 재사용하는 것으로 성능 최적화를 위해 사용함. 첫 번째 파라미터에 로직 함수를, 두 전째 파라미터에 확인할 변수를 넣어 주면 됨.

```jsx
function countActiveUsers(users) {
  console.log('활성 사용자 수를 세는중...');
  return users.filter(user => user.active).length;
}

function App() {
    const onChange = e => {
        const { name, value } = e.target;
        setInputs({
        ...inputs,
        [name]: value
        });
    };
    
    const [users, setUsers] = useState([
        {
            id: 1,
            username: 'velopert',
            email: 'public.velopert@gmail.com',
            active: true
        },
        {
            id: 2,
            username: 'tester',
            email: 'tester@example.com',
            active: false
        },
        {
            id: 3,
            username: 'liz',
            email: 'liz@example.com',
            active: false
        }
    ]);


// const count = countActiveUsers(users);
 const count = useMemo(() => countActiveUsers(users), [users]);
}

```

</details>

<details>
    <summary>useReducer</summary>

- useState처럼 상태 업데이트에 사용됨.
- useState와 달리 상태 업데이트 로직을 분리할 수 있음.

```jsx
import React, { useReducer } from 'react';

function reducer(state, action) {
  switch (action.type) {
    case 'INCREMENT':
      return state + 1;
    case 'DECREMENT':
      return state - 1;
    default:
      return state;
  }
}

function Counter() {
  const [number, dispatch] = useReducer(reducer, 0);

  const onIncrease = () => {
    dispatch({ type: 'INCREMENT' });
  };

  const onDecrease = () => {
    dispatch({ type: 'DECREMENT' });
  };

  return (
    <div>
      <h1>{number}</h1>
      <button onClick={onIncrease}>+1</button>
      <button onClick={onDecrease}>-1</button>
    </div>
  );
}

export default Counter;

```

</details>

<details>
    <summary>커스텀 Hooks 만들기</summary>

- 반복되는 로직을 쉽게 재사용하기 위해 만듬.
- 보통 use로 시작하는 파일을 만들고 그 안에 함수 작성.
- 함수 작성 시 useState, useEffect, useReducer, useCallBak 등 Hooks를 사용하여 원하는 기능을 만들고 값을 반환하면 됨.
</details>


