insert into
    users (email, password)
values
    ('admin@bp.test', '{noop}adminpass'),   -- 1
    ('adjuster@bp.test', '{noop}adjusterpass'),     -- 2
    ('supervisors@bp.test', '{noop}supervisorspass'); -- 3

insert into
    user_role (name, description)
values
    ('ADMIN', 'pełne uprawnienia'),   -- 1
    ('CLAIMS_ADJUSTER', 'uprawnienia do konfiguracji, obsługi polis i szkód + wypłata odszkodowań'),   -- 2
    ('SUPERVISORS', 'uprawnienia do tworzenia i obsługi polis i szkód');   -- 3

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2, 2),
    (3, 3);