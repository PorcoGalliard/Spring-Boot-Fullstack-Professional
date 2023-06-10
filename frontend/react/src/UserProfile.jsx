const UserProfile = ({name, gender, age, tagNumber, ...props}) => {
    gender = gender === "MALE" ? "men" : "women";

    return (
        <div>
            <p>{ name }</p>
            <img src={`https://randomuser.me/api/portraits/${ gender }/${ tagNumber }.jpg`} />
            {props.children}
        </div>
    )
}

export default UserProfile