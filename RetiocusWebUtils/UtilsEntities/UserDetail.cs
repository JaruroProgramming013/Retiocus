using System;
using System.Threading.Tasks;
using FirebaseAdmin.Auth;

namespace UtilsEntities
{
    public class UserDetail
    {
        public string uid { get; set; }
        public string username { get; set; }

        public UserDetail(string uid, string username)
        {
            this.uid = uid;
            this.username = username;
        }

        public static async Task<UserDetail> fromUid(string uid)
        {
            UserRecord record = await FirebaseAuth.DefaultInstance.GetUserAsync(uid);

            return new UserDetail(record.Uid, record.DisplayName);
        }
    }
}
