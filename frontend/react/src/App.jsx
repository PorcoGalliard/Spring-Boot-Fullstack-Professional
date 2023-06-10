import UserProfile from "./UserProfile.jsx";
import { useState } from "react";

const users = [
    {
        name: "Jamila",
        age: 22,
        gender: "FEMALE",
    },
    {
        name: "Alfian",
        age: 23,
        gender: "FEMALE",
    },
    {
        name: "Antonio",
        age: 25,
        gender: "MALE",
    },
    {
        name: "Marc",
        age: 26,
        gender: "MALE",
    },
    {
        name: "Blossom",
        age: 27,
        gender: "FEMALE",
    },
]

const UserProfiles = ( {users} ) => (
    <div>
        { users.map((user, index) => (
            <UserProfile
                key={index}
                name={user.name}
                age={user.age}
                gender={user.gender}
                tagNumber={index}
            />
        ))}
    </div>
)
function App() {

    const [counter, setCounter] = useState(0);

    return (
        <div>
            <button onClick={() => setCounter(prevCounter => prevCounter + 1)}>
                Increment Button
            </button>
            <h1>{counter}</h1>
            <UserProfiles users={users}/>
        </div>
    )
}

export default App
